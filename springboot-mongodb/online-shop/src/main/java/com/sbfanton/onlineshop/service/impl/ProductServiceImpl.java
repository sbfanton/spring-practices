package com.sbfanton.onlineshop.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Triple;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.stereotype.Service;

import com.sbfanton.onlineshop.model.Product;
import com.sbfanton.onlineshop.model.dto.ProductDTO;
import com.sbfanton.onlineshop.model.dto.ProductDTOExtended;
import com.sbfanton.onlineshop.model.mappers.ProductMapper;
import com.sbfanton.onlineshop.repository.ProductRepository;
import com.sbfanton.onlineshop.service.ProductService;
import com.sbfanton.onlineshop.utils.EnumUtils;
import com.sbfanton.onlineshop.utils.ParamsConverter;
import com.sbfanton.onlineshop.utils.ParamsValidator;
import com.sbfanton.onlineshop.utils.constants.CollectionsNames;
import com.sbfanton.onlineshop.utils.constants.ProductReqAllowedParams;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

    public ProductDTO createProduct(ProductDTO product) throws Exception {
        Product newProduct = productRepository.save(ProductMapper.toEntity(product));
        return ProductMapper.toDTO(newProduct);
    }

    public ProductDTO updateProduct(String id, ProductDTO productDetails) throws Exception {
        Product prod = productRepository.findById(id).map(product -> {
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());
            product.setFacturerId(productDetails.getFacturerId());
            product.setFeatures(productDetails.getFeatures());
            product.setCompatibility(productDetails.getCompatibility());
            product.setIncludedItems(productDetails.getIncludedItems());
            return productRepository.save(product);
        }).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return ProductMapper.toDTO(prod);
    }

    public void deleteProductById(String id) {
        productRepository.deleteById(id);
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductDTOExtended> getProductDTOList(Map<String, String> filters) throws Exception {
		
		ParamsValidator.validateParams(
				filters, 
				EnumUtils.getEnumPropertyValues(ProductReqAllowedParams.class, "getParamName"));
		
		List<Triple<String, Class<?>, Object>> convertedFilters = 
				ParamsConverter.convertReqParamsMapToGenericMap(filters, Product.class.getName(), null);
		
		Document initialMatch = productRepository.getDocumentsFilteredMatch(
				convertedFilters, 
				Product.class, 
				CollectionsNames.PRODUCTS.getName());
		
		List<AggregationOperation> aggOpList = productRepository.generateProductAggregationList(initialMatch);
		
		List<ProductDTOExtended> products = (List<ProductDTOExtended>) productRepository.getCustomModelListWithAgg(
				aggOpList, 
				Product.class, 
				ProductDTOExtended.class);
		
		return products;
	}

	@Override
	public ProductDTOExtended getProductDTOById(String id) throws Exception {
		Document match = productRepository.getDocumentMatchById(id);
        List<AggregationOperation> aggOpList = productRepository.generateProductAggregationList(match);
    	return (ProductDTOExtended) productRepository
    			.getCustomModelByIdWithAgg(aggOpList, Product.class, ProductDTOExtended.class)
    	        .orElseThrow(() -> new Exception("Product not found"));
	}
}

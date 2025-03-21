package com.sbfanton.online_shop.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbfanton.online_shop.model.Product;
import com.sbfanton.online_shop.repository.ProductRepository;
import com.sbfanton.online_shop.service.ProductService;

import utils.EnumUtils;
import utils.ParamsConverter;
import utils.ParamsValidator;
import utils.constants.CollectionsNames;
import utils.constants.ProductReqAllowedParams;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductByProductId(String id) {
        return productRepository.findByProductId(Integer.parseInt(id));
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(String id, Product productDetails) {
        return productRepository.findByProductId(Integer.parseInt(id)).map(product -> {
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());
            product.setFacturer(productDetails.getFacturer());
            product.setFeatures(productDetails.getFeatures());
            product.setCompatibility(productDetails.getCompatibility());
            product.setIncludedItems(productDetails.getIncludedItems());
            return productRepository.save(product);
        }).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public void deleteProductByProductId(String id) {
        productRepository.deleteByProductId(Integer.parseInt(id));
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getProductsFiltered(Map<String, String> filters) throws Exception {
		ParamsValidator.validateParams(
				filters, 
				EnumUtils.getEnumPropertyValues(ProductReqAllowedParams.class, "getParamName"));
		List<Triple<String, Class<?>, Object>> convertedFilters = 
				ParamsConverter.convertReqParamsMapToGenericMap(filters, Product.class.getName());
		List<Product> products = (List<Product>) productRepository
				.searchDocumentsFiltered(convertedFilters, Product.class, CollectionsNames.PRODUCTS.getName(), null);
		
		return products;
	}
}

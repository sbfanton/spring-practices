package com.sbfanton.onlineshop.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbfanton.onlineshop.model.Product;
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
	
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(String id, Product productDetails) {
        return productRepository.findById(id).map(product -> {
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());
            product.setFacturerId(productDetails.getFacturerId());
            product.setFeatures(productDetails.getFeatures());
            product.setCompatibility(productDetails.getCompatibility());
            product.setIncludedItems(productDetails.getIncludedItems());
            return productRepository.save(product);
        }).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public void deleteProductById(String id) {
        productRepository.deleteById(id);
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getProductsFiltered(Map<String, String> filters) throws Exception {
		ParamsValidator.validateParams(
				filters, 
				EnumUtils.getEnumPropertyValues(ProductReqAllowedParams.class, "getParamName"));
		List<Triple<String, Class<?>, Object>> convertedFilters = 
				ParamsConverter.convertReqParamsMapToGenericMap(filters, Product.class.getName(), null);
		List<Product> products = (List<Product>) productRepository
				.searchDocumentsFiltered(convertedFilters, Product.class, CollectionsNames.PRODUCTS.getName());
		
		return products;
	}
}

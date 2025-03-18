package com.sbfanton.online_shop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbfanton.online_shop.model.Product;
import com.sbfanton.online_shop.repository.ProductRepository;
import com.sbfanton.online_shop.service.ProductService;

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
            product.setFacturer(productDetails.getFacturer());
            product.setFeatures(productDetails.getFeatures());
            product.setCompatibility(productDetails.getCompatibility());
            product.setIncludedItems(productDetails.getIncludedItems());
            return productRepository.save(product);
        }).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

	public List<Product> getProductsByNameLike(String name) {
		return productRepository.findByNameLike(name);
	}

	public List<Product> getProductsByFacturer(String facturer) {
		return productRepository.findByFacturer(facturer);
	}

}

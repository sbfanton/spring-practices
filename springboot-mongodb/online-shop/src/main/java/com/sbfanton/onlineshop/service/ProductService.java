package com.sbfanton.onlineshop.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.sbfanton.onlineshop.model.Product;

public interface ProductService {

    public List<Product> getAllProducts();
    public Optional<Product> getProductById(String id);
    public Product createProduct(Product product);
    public Product updateProduct(String id, Product productDetails);
    public void deleteProductById(String id);
    public List<Product> getProductsFiltered(Map<String, String> filters) throws Exception;
}

package com.sbfanton.online_shop.service;

import java.util.List;
import java.util.Optional;

import com.sbfanton.online_shop.model.Product;

public interface ProductService {

    public List<Product> getAllProducts();
    public Optional<Product> getProductById(String id);
    public List<Product> getProductsByNameLike(String name);
    public List<Product> getProductsByFacturer(String facturer);
    public Product createProduct(Product product);
    public Product updateProduct(String id, Product productDetails);
    public void deleteProduct(String id);
}

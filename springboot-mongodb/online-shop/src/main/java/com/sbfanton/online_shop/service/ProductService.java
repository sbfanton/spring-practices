package com.sbfanton.online_shop.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.sbfanton.online_shop.model.Product;

public interface ProductService {

    public List<Product> getAllProducts();
    public Optional<Product> getProductByProductId(String id);
    public Product createProduct(Product product);
    public Product updateProduct(String id, Product productDetails);
    public void deleteProductByProductId(String id);
    public List<Product> getProductsFiltered(Map<String, String> filters) throws Exception;
}

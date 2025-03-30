package com.sbfanton.onlineshop.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.sbfanton.onlineshop.model.Product;
import com.sbfanton.onlineshop.model.dto.ProductDTO;

public interface ProductService {

    public List<Product> getAllProducts();
    public Optional<Product> getProductById(String id);
    public ProductDTO getProductDTOById(String id) throws Exception;
    public Product createProduct(Product product);
    public Product updateProduct(String id, Product productDetails);
    public void deleteProductById(String id);
    public List<ProductDTO> getProductDTOList(Map<String, String> filters) throws Exception;
}

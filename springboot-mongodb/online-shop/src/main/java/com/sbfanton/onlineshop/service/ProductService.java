package com.sbfanton.onlineshop.service;

import java.util.List;
import java.util.Map;

import com.sbfanton.onlineshop.model.dto.ProductDTO;
import com.sbfanton.onlineshop.model.dto.ProductDTOExtended;

public interface ProductService {

    public ProductDTOExtended getProductDTOById(String id) throws Exception;
    public ProductDTO createProduct(ProductDTO product) throws Exception;
    public ProductDTO updateProduct(String id, ProductDTO productDetails) throws Exception;
    public void deleteProductById(String id);
    public List<ProductDTOExtended> getProductDTOList(Map<String, String> filters) throws Exception;
}

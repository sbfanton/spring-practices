package com.course.springsecuritycourse.service;

import java.util.List;

import com.course.springsecuritycourse.dto.StatusDTO;
import com.course.springsecuritycourse.entity.Product;

public interface ProductService {

    public List<Product> getAllproducts();
    public Product getProductById(Long id);
    public Product saveProduct(Product product);
    public StatusDTO deleteProductById(Long id);
}

package com.course.springsecuritycourse.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.springsecuritycourse.dto.StatusDTO;
import com.course.springsecuritycourse.entity.Product;
import com.course.springsecuritycourse.repository.ProductRepository;
import com.course.springsecuritycourse.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllproducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public StatusDTO deleteProductById(Long id) {
        productRepository.deleteById(id);
        StatusDTO statusDTO = new StatusDTO();
        statusDTO.setMessage("Producto con id " + id + " eliminado.");
        return statusDTO;
    }

}

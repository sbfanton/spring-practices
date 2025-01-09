package com.course.springsecuritycourse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.course.springsecuritycourse.dto.StatusDTO;
import com.course.springsecuritycourse.entity.Product;
import com.course.springsecuritycourse.service.ProductService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("${corporation.api}/products")
public class ProductController {
    
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        List<Product> products = productService.getAllproducts();
        return new ResponseEntity(products, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return new ResponseEntity(product, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveProduct(@RequestBody @Valid Product product) {
        Product savedProduct = productService.saveProduct(product);
        return new ResponseEntity(savedProduct, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable Long id) {
        StatusDTO statusDTO = productService.deleteProductById(id);
        return new ResponseEntity(statusDTO, HttpStatus.OK);
    }
}

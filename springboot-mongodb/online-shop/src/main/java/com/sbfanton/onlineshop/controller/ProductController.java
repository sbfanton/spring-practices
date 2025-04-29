package com.sbfanton.onlineshop.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sbfanton.onlineshop.model.dto.ProductDTO;
import com.sbfanton.onlineshop.model.dto.ProductDTOExtended;
import com.sbfanton.onlineshop.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTOExtended>> getAllProducts(@RequestParam Map<String, String> filters) throws Exception {
        
    	return ResponseEntity.ok(productService.getProductDTOList(filters));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTOExtended> getProductById(@PathVariable String id) throws Exception {
    	return ResponseEntity.ok(productService.getProductDTOById(id));
    }

    @PostMapping
    public ProductDTO createProduct(@RequestBody ProductDTO product) throws Exception {
        return productService.createProduct(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable String id, @RequestBody ProductDTO productDetails) throws Exception {
        try {
            ProductDTO updatedProduct = productService.updateProduct(id, productDetails);
            return ResponseEntity.ok(updatedProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }
}

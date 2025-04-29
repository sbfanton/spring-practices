package com.sbfanton.onlineshop.model.mappers;

import com.sbfanton.onlineshop.model.Product;
import com.sbfanton.onlineshop.model.dto.ProductDTO;

public class ProductMapper {

    public static ProductDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }

        return ProductDTO.builder()
            .id(product.getId())
            .name(product.getName())
            .description(product.getDescription())
            .price(product.getPrice())
            .facturerId(product.getFacturerId())
            .features(product.getFeatures())
            .compatibility(product.getCompatibility())
            .includedItems(product.getIncludedItems())
            .build();
    }

    public static Product toEntity(ProductDTO productDTO) {
        if (productDTO == null) {
            return null;
        }

        return Product.builder()
            .id(productDTO.getId())
            .name(productDTO.getName())
            .description(productDTO.getDescription())
            .price(productDTO.getPrice())
            .facturerId(productDTO.getFacturerId())
            .features(productDTO.getFeatures())
            .compatibility(productDTO.getCompatibility())
            .includedItems(productDTO.getIncludedItems())
            .build();
    }
}

package com.sbfanton.onlineshop.model.mappers;

import com.sbfanton.onlineshop.model.Facturer;
import com.sbfanton.onlineshop.model.dto.FacturerDTO;

public class FacturerMapper {

	public static FacturerDTO toDTO(Facturer facturer) {
        if (facturer == null) {
            return null;
        }

        return FacturerDTO.builder()
        		.id(facturer.getId())
        		.name(facturer.getName())
        		.founded(facturer.getFounded())
        		.country(facturer.getCountry())
        		.branches(facturer.getBranches())
        		.products(facturer.getProducts())
        		.build();
    }
	
	public static Facturer toEntity(FacturerDTO facturerDTO) {
        if (facturerDTO == null) {
            return null;
        }

        return Facturer.builder()
        		.id(facturerDTO.getId())
        		.name(facturerDTO.getName())
        		.founded(facturerDTO.getFounded())
        		.country(facturerDTO.getCountry())
        		.branches(facturerDTO.getBranches())
        		.products(facturerDTO.getProducts())
        		.build();
    }
}

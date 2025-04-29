package com.sbfanton.onlineshop.model.mappers;

import com.sbfanton.onlineshop.model.Customer;
import com.sbfanton.onlineshop.model.dto.CustomerDTO;

public class CustomerMapper {

    public static CustomerDTO toDTO(Customer customer) {
        if (customer == null) {
            return null;
        }

        return CustomerDTO.builder()
            .id(customer.getId())
            .name(customer.getName())
            .nationalDocumentId(customer.getNationalDocumentId())
            .email(customer.getEmail())
            .phoneNumber(customer.getPhoneNumber())
            .address(customer.getAddress())
            .build();
    }
    
    public static Customer toEntity(CustomerDTO customerDTO) {
    	if (customerDTO == null) {
            return null;
        }

        return Customer.builder()
            .id(customerDTO.getId())
            .name(customerDTO.getName())
            .nationalDocumentId(customerDTO.getNationalDocumentId())
            .email(customerDTO.getEmail())
            .phoneNumber(customerDTO.getPhoneNumber())
            .address(customerDTO.getAddress())
            .build();
    }
}


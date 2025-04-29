package com.sbfanton.onlineshop.model.dto;

import com.sbfanton.onlineshop.model.Address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

	private String id;
	private String nationalDocumentId;
    private String name;
    private String email;
    private String phoneNumber;
    private Address address;
}

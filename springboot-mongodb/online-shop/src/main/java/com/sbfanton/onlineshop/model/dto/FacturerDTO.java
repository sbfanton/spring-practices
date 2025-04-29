package com.sbfanton.onlineshop.model.dto;

import java.util.List;

import com.sbfanton.onlineshop.model.Address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FacturerDTO {

	private String id;
	private String name;
	private String country;
	private Integer founded;
	private List<Address> branches;
	private List<String> products;
}

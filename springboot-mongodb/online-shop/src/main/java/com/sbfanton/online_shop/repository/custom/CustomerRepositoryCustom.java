package com.sbfanton.online_shop.repository.custom;

import java.util.List;
import java.util.Map;

import com.sbfanton.online_shop.model.Customer;

public interface CustomerRepositoryCustom {

	List<Customer> searchCustomersFiltered(Map<String, Object> filters);
}

package com.sbfanton.onlineshop.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbfanton.onlineshop.model.Facturer;
import com.sbfanton.onlineshop.repository.FacturerRepository;
import com.sbfanton.onlineshop.service.FacturerService;

@Service
public class FacturerServiceImpl implements FacturerService {

	@Autowired
	private FacturerRepository facturerRepository;
	
	@Override
	public Facturer createFacturer(Facturer facturer) throws Exception {
		return facturerRepository.save(facturer);
	}

	@Override
	public List<Facturer> getAllFacturers() {
		return facturerRepository.findAll();
	}

	@Override
	public Optional<Facturer> getFacturerById(String id) {
		return facturerRepository.findById(id);
	}

	@Override
	public List<Facturer> getFacturersFiltered(Map<String, String> filters) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Facturer updateFacturer(String id, Facturer updatedFacturer) {
		return facturerRepository.findById(id).map(facturer -> {
        	facturer.setName(updatedFacturer.getName());
            facturer.setFounded(updatedFacturer.getFounded());
            facturer.setCountry(updatedFacturer.getCountry());
            facturer.setBranches(updatedFacturer.getBranches());
            facturer.setProducts(updatedFacturer.getProducts());
            return facturerRepository.save(facturer);
        }).orElseThrow(() -> new RuntimeException("Fabricante no encontrado"));
	}

	@Override
	public void deleteFacturerById(String id) {
		facturerRepository.deleteById(id);
	}
}

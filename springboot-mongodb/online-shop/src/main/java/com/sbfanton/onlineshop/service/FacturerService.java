package com.sbfanton.onlineshop.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.sbfanton.onlineshop.model.Facturer;

public interface FacturerService {

	public Facturer createFacturer(Facturer facturer) throws Exception;
    public List<Facturer> getAllFacturers();
    public Optional<Facturer> getFacturerById(String id);
    public List<Facturer> getFacturersFiltered(Map<String, String> filters) throws Exception;
    public Facturer updateFacturer(String id, Facturer updatedFacturer);
    public void deleteFacturerById(String id);
}

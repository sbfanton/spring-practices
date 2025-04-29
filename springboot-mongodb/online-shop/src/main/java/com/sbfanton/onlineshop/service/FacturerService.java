package com.sbfanton.onlineshop.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.sbfanton.onlineshop.model.dto.FacturerDTO;

public interface FacturerService {

	public FacturerDTO createFacturer(FacturerDTO facturer) throws Exception;
    public Optional<FacturerDTO> getFacturerById(String id);
    public List<FacturerDTO> getAllFacturers(Map<String, String> filters) throws Exception;
    public FacturerDTO updateFacturer(String id, FacturerDTO updatedFacturer);
    public void deleteFacturerById(String id);
}

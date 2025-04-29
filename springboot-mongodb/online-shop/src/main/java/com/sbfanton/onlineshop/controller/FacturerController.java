package com.sbfanton.onlineshop.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

import com.sbfanton.onlineshop.model.dto.FacturerDTO;
import com.sbfanton.onlineshop.service.FacturerService;

@RestController
@RequestMapping("/facturers")
public class FacturerController {

	@Autowired
	private FacturerService facturerService;
	
	@GetMapping
    public ResponseEntity<List<FacturerDTO>> getAllFacturers(@RequestParam Map<String, String> filters) throws Exception {
    	return ResponseEntity.ok(facturerService.getAllFacturers(filters));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacturerDTO> getFacturerById(@PathVariable String id) {
        Optional<FacturerDTO> facturer = facturerService.getFacturerById(id);
        return facturer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public FacturerDTO createFacturer(@RequestBody FacturerDTO facturer) throws Exception {
        return facturerService.createFacturer(facturer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FacturerDTO> updateFacturer(@PathVariable String id, @RequestBody FacturerDTO facturerDetails) {
        try {
            FacturerDTO updatedFacturer = facturerService.updateFacturer(id, facturerDetails);
            return ResponseEntity.ok(updatedFacturer);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFacturer(@PathVariable String id) {
        facturerService.deleteFacturerById(id);
        return ResponseEntity.noContent().build();
    }
}

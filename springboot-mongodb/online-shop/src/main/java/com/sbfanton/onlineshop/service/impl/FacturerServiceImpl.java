package com.sbfanton.onlineshop.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Triple;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.stereotype.Service;

import com.sbfanton.onlineshop.model.Facturer;
import com.sbfanton.onlineshop.model.dto.FacturerDTO;
import com.sbfanton.onlineshop.model.mappers.FacturerMapper;
import com.sbfanton.onlineshop.repository.FacturerRepository;
import com.sbfanton.onlineshop.service.FacturerService;
import com.sbfanton.onlineshop.utils.EnumUtils;
import com.sbfanton.onlineshop.utils.ParamsConverter;
import com.sbfanton.onlineshop.utils.ParamsValidator;
import com.sbfanton.onlineshop.utils.constants.CollectionsNames;
import com.sbfanton.onlineshop.utils.constants.FacturerListTypeParamProps;
import com.sbfanton.onlineshop.utils.constants.FacturerReqAllowedParams;

@Service
public class FacturerServiceImpl implements FacturerService {

	@Autowired
	private FacturerRepository facturerRepository;
	
	@Override
	public FacturerDTO createFacturer(FacturerDTO facturer) throws Exception {
		return FacturerMapper.toDTO(facturerRepository.save(FacturerMapper.toEntity(facturer)));
	}

	@Override
	public Optional<FacturerDTO> getFacturerById(String id) {
		return facturerRepository.findById(id)
				.map(FacturerMapper::toDTO);
	}

	@Override
	public FacturerDTO updateFacturer(String id, FacturerDTO updatedFacturer) {
		return facturerRepository.findById(id).map(facturer -> {
        	facturer.setName(updatedFacturer.getName());
            facturer.setFounded(updatedFacturer.getFounded());
            facturer.setCountry(updatedFacturer.getCountry());
            facturer.setBranches(updatedFacturer.getBranches());
            facturer.setProducts(updatedFacturer.getProducts());
            return facturerRepository.save(facturer);
        })
		.map(FacturerMapper::toDTO)
		.orElseThrow(() -> new RuntimeException("Fabricante no encontrado"));
	}

	@Override
	public void deleteFacturerById(String id) {
		facturerRepository.deleteById(id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FacturerDTO> getAllFacturers(Map<String, String> filters) throws Exception {
		ParamsValidator.validateParams(
				filters, 
				EnumUtils.getEnumPropertyValues(FacturerReqAllowedParams.class, "getParamName"));
		
		List<Triple<String, Class<?>, Object>> convertedFilters = 
				ParamsConverter.convertReqParamsMapToGenericMap(
						filters, 
						Facturer.class.getName(), 
						FacturerListTypeParamProps.listAttrsTypesMap);
		
		Document initialMatch = facturerRepository.getDocumentsFilteredMatch(
				convertedFilters, 
				Facturer.class, 
				CollectionsNames.FACTURERS.getName());
		
		List<AggregationOperation> aggOpList = facturerRepository.generateFacturerAggregationList(initialMatch);
		
		List<Facturer> facturers = (List<Facturer>) facturerRepository.getCustomModelListWithAgg(
				aggOpList, 
				Facturer.class, 
				Facturer.class);
		
		return facturers.stream()
				.map(FacturerMapper::toDTO)
				.collect(Collectors.toList());
	}
}

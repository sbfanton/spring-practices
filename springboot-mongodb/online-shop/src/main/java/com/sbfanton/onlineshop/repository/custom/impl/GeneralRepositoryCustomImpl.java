package com.sbfanton.onlineshop.repository.custom.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Triple;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.sbfanton.onlineshop.repository.custom.GeneralRepositoryCustom;
import com.sbfanton.onlineshop.utils.ParamsConverter;
import com.sbfanton.onlineshop.utils.constants.Separators;

public class GeneralRepositoryCustomImpl implements GeneralRepositoryCustom {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
    public Document getDocumentsFilteredMatch(
    		List<Triple<String, Class<?>, Object>> filters, 
    		Class<?> entityClass, 
    		String collectionName) throws Exception {
		
        Query query = new Query();

        for (Triple<String, Class<?>, Object> item : filters) {
            String attr = item.getLeft();
            Class<?> clazz = item.getMiddle();
            Object value = item.getRight();

            if (clazz == String.class) {
                query.addCriteria(Criteria.where(attr).regex((String)value.toString(), "i")); // Búsqueda con regex (ignora mayúsculas)
            } 
            else if (clazz.isAssignableFrom(List.class)) {
            	// Busqueda para items de objetos internos de listas
            	List<Criteria> criterias = getCriteriaListForItemInListTypeSearch(attr, value);
            	query.addCriteria(new Criteria().andOperator(criterias));
            } 
            else {
                query.addCriteria(Criteria.where(attr).is(value)); // Búsqueda exacta para otros tipos de datos
            }
        }
        
        Document matchStage = new Document("$match", query.getQueryObject());
        return matchStage;

    }

	private List<Criteria> getCriteriaListForItemInListTypeSearch(String attr, Object value) throws ClassNotFoundException {
		
		List<Criteria> criterias = new ArrayList<>();
    	String[] splittedAttrName = attr.split(Separators.ITEM_SEPARATOR);
    	String[] splittedAttrValue = ((String)value).split(Separators.ITEM_SEPARATOR);
    	
    	for(int i = 0; i < splittedAttrName.length; i++) {
    		String[] splitted = splittedAttrName[i].split("\\.");
        	String listAttrName = splitted[0];
        	String attrName = splitted[1];
	        Class<?> listAttrClazz = Class.forName(((String)splittedAttrValue[i]).split(Separators.ITEM_VALUE_CLASS_SEPARATOR)[1]);
	        Object val = ParamsConverter.convertParamToType(listAttrClazz, ((String)splittedAttrValue[i]).split(Separators.ITEM_VALUE_CLASS_SEPARATOR)[0]);
	        
	        Criteria criteria;
	        
	        if(listAttrClazz == String.class) {
    			criteria = Criteria.where(listAttrName)
		        				.elemMatch(Criteria.where(attrName).regex((String) val, "i"));  
		    }
	        else {
	        	criteria = Criteria.where(listAttrName)
		        				.elemMatch(Criteria.where(attrName).is(val));  
	        }
	        
	        criterias.add(criteria);
    	}
    	
    	return criterias;
	}

	@Override
	public Document getDocumentMatchById(String id) throws Exception {
		
		Document doc = new Document("$match",
		        new Document("_id", new ObjectId(id)) // Convertimos el ID a ObjectId
			    );
		
		return doc;
	}
	
	@Override
	public Optional<?> getCustomModelByIdWithAgg(
			List<AggregationOperation> aggregationOperations,
			Class<?> collectionClass,
			Class<?> customReturnClass) throws Exception {
		
		Aggregation agg = Aggregation.newAggregation(aggregationOperations);
			AggregationResults<?> results = mongoTemplate.aggregate(
			    agg, mongoTemplate.getCollectionName(collectionClass), customReturnClass
			);

			return Optional.ofNullable(results.getUniqueMappedResult());
	}
	
	@Override
	public List<?> getCustomModelListWithAgg(
			List<AggregationOperation> aggregationOperations,
			Class<?> collectionClass,
			Class<?> customReturnClass) throws Exception {
		
		Aggregation agg = Aggregation.newAggregation(aggregationOperations);
			AggregationResults<?> results = mongoTemplate.aggregate(
			    agg, mongoTemplate.getCollectionName(collectionClass), customReturnClass
			);

			return results.getMappedResults();
	}
}

package com.sbfanton.onlineshop.repository.custom.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;

import com.sbfanton.onlineshop.model.Facturer;
import com.sbfanton.onlineshop.repository.custom.ProductRepositoryCustom;

public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public List<AggregationOperation> generateProductAggregationList(Document initialMatchStage) {
		
		List<AggregationOperation> aggregationOperations = new ArrayList<>();
		
		if(initialMatchStage != null)
			aggregationOperations.add(context -> initialMatchStage);
		
		aggregationOperations.add(context -> new Document("$lookup",
		        new Document("from", mongoTemplate.getCollectionName(Facturer.class))
		            .append("let", new Document("facturerId", new Document("$toObjectId", "$facturerId")))
		            .append("pipeline", Arrays.asList(
		                new Document("$match",
		                    new Document("$expr",
		                        new Document("$eq", Arrays.asList("$_id", "$$facturerId"))
		                    )
		                )
		            ))
		            .append("as", "facturer")
		    ));
		
		aggregationOperations.add(context -> new Document("$set", 
				new Document("facturer", 
						new Document()
						.append("id", "$facturer._id")
						.append("name", "$facturer.name")
		)));
	
		aggregationOperations.add(Aggregation.unwind("facturer", true));
	
		
		return aggregationOperations;
	}

}

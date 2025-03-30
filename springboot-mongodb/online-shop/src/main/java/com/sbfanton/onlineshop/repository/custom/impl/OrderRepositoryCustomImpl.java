package com.sbfanton.onlineshop.repository.custom.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;

import com.sbfanton.onlineshop.model.Customer;
import com.sbfanton.onlineshop.model.Product;
import com.sbfanton.onlineshop.repository.custom.OrderRepositoryCustom;

public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	public List<AggregationOperation> generateOrderAggregationList(Document initialMatchStage) {
		
		List<AggregationOperation> aggregationOperations = new ArrayList<>();
		
		// Filtro de busqueda inicial
		if(initialMatchStage != null)
			aggregationOperations.add(context -> initialMatchStage);
		
		// Lookup para obtener el Customer asociado al Order
		aggregationOperations.add(context -> new Document("$lookup",
			        new Document("from", mongoTemplate.getCollectionName(Customer.class))
			            .append("let", new Document("customerId", new Document("$toObjectId", "$customerId")))
			            .append("pipeline", Arrays.asList(
			                new Document("$match",
			                    new Document("$expr",
			                        new Document("$eq", Arrays.asList("$_id", "$$customerId"))
			                    )
			                )
			            ))
			            .append("as", "customer")
			    ));
		
		
		aggregationOperations.add(context -> new Document("$set", 
				new Document("customer", 
						new Document()
						.append("id", "$customer._id")
						.append("name", "$customer.name")
		)));
		
		// Permite valores nulos en caso de que no haya coincidencia
		aggregationOperations.add(Aggregation.unwind("customer", true));
				
		
		// Lookup para obtener los productos dentro de Items
		aggregationOperations.add(context -> new Document("$lookup",
			        new Document("from", mongoTemplate.getCollectionName(Product.class))
			            .append("let", new Document("productId", new Document("$map", 
			                new Document("input", "$items")
			                .append("as", "item")
			                .append("in", new Document("$toObjectId", "$$item.productId")) // Conversión a ObjectId
			            )))
			            .append("pipeline", Arrays.asList(
			                new Document("$match",
			                    new Document("$expr",
			                        new Document("$in", Arrays.asList("$_id", "$$productId")) // Busca en lista de productIds
			                    )
			                )
			            ))
			            .append("as", "products")
			    ));
				
		
		// Reemplazar los IDs de productos en items con los objetos completos
		aggregationOperations.add(context -> new Document("$set",
		    new Document("items", new Document("$map",
		        new Document("input", "$items")
		            .append("as", "item")
		            .append("in", new Document()
		            .append("prodId", new Document("$arrayElemAt", Arrays.asList(
		                "$products._id", new Document("$indexOfArray", Arrays.asList("$products._id", new Document("$toObjectId", "$$item.productId")))
		            )))
		            .append("name", new Document("$arrayElemAt", Arrays.asList(
		                "$products.name", new Document("$indexOfArray", Arrays.asList("$products._id", new Document("$toObjectId", "$$item.productId")))
		            )))
		            .append("description", new Document("$arrayElemAt", Arrays.asList(
		                "$products.description", new Document("$indexOfArray", Arrays.asList("$products._id", new Document("$toObjectId", "$$item.productId")))
		            )))
		            .append("price", new Document("$arrayElemAt", Arrays.asList(
		                "$products.price", new Document("$indexOfArray", Arrays.asList("$products._id", new Document("$toObjectId", "$$item.productId")))
		            )))
		            .append("quantity", "$$item.quantity") // Mantiene la cantidad
		        ))
		    )));
		
		
		// Eliminar el array auxiliar "products"
		aggregationOperations.add(context -> new Document("$unset", "products"));
				
		
		return aggregationOperations;
	}
}

package com.sbfanton.onlineshop.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sbfanton.onlineshop.model.Facturer;
import com.sbfanton.onlineshop.repository.custom.FacturerRepositoryCustom;
import com.sbfanton.onlineshop.repository.custom.GeneralRepositoryCustom;

public interface FacturerRepository extends MongoRepository<Facturer, String>, GeneralRepositoryCustom, FacturerRepositoryCustom {

}

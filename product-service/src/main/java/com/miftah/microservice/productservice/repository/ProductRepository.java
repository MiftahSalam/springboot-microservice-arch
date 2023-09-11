package com.miftah.microservice.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.miftah.microservice.productservice.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {

}

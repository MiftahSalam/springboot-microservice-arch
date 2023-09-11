package com.example.miftah.microservice.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.miftah.microservice.orderservice.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}

package com.example.miftah.microservice.orderservice.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.miftah.microservice.orderservice.dto.CreateOrderRequest;
import com.example.miftah.microservice.orderservice.service.OrderService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public CompletableFuture<String> placeOrder(@RequestBody CreateOrderRequest request) {
        orderService.placeOrder(request);

        return CompletableFuture.supplyAsync(() -> "Order create successfully");
    }

    public CompletableFuture<String> fallbackMethod(CreateOrderRequest request, RuntimeException exception) {
        return CompletableFuture.supplyAsync(() -> "Oops! something when wrong, please order again after sometime");
    }
}

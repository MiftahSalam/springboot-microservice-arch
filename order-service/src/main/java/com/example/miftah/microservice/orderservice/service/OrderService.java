package com.example.miftah.microservice.orderservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.miftah.microservice.orderservice.dto.CreateOrderRequest;
import com.example.miftah.microservice.orderservice.dto.InventoryResponse;
import com.example.miftah.microservice.orderservice.dto.OrderLineItemsRequest;
import com.example.miftah.microservice.orderservice.event.OrderPlacedEvent;
import com.example.miftah.microservice.orderservice.model.Order;
import com.example.miftah.microservice.orderservice.model.OrderLineItem;
import com.example.miftah.microservice.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public void placeOrder(@RequestBody CreateOrderRequest request) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItem> orderLineItems = request.getOrderLineItemsRequests().stream()
                .map(arg0 -> toOrderLineItems(arg0)).toList();

        order.setOrderLineItems(orderLineItems);

        List<String> skus = order.getOrderLineItems().stream().map(arg0 -> arg0.getSku()).toList();

        InventoryResponse[] result = webClient.build().get()
                .uri("http://inventory-service/api/inventory", arg0 -> arg0.queryParam("skus", skus).build()).retrieve()
                .bodyToMono(InventoryResponse[].class).block();
        boolean allMatch = Arrays.stream(result).allMatch(arg0 -> {
            System.out.println(arg0);
            return arg0.isInStock();
        });

        if (allMatch) {
            orderRepository.save(order);
            kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrderNumber()));
        } else {
            throw new IllegalArgumentException("Product is out of stock");
        }
    }

    private OrderLineItem toOrderLineItems(OrderLineItemsRequest request) {
        OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setPrice(request.getPrice());
        orderLineItem.setQuantity(request.getQuantity());
        orderLineItem.setSku(request.getSku());

        return orderLineItem;
    }
}

package com.example.miftah.microservice.orderservice.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateOrderRequest {
    private List<OrderLineItemsRequest> orderLineItemsRequests;
}

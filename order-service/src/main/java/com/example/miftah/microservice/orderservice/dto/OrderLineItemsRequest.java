package com.example.miftah.microservice.orderservice.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderLineItemsRequest {
    private Long id;

    private String sku;

    private BigDecimal price;

    private Integer quantity;

}

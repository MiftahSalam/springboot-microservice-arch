package com.example.miftah.microservice.inventoryservice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.miftah.microservice.inventoryservice.dto.InventoryResponse;
import com.example.miftah.microservice.inventoryservice.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skus) {
        return inventoryRepository.findBySkuIn(skus).stream().map(arg0 -> {
            System.out.println(arg0);
            return InventoryResponse.builder()
                    .sku(arg0.getSku()).inStock(arg0.getQuantity() > 0).build();
        }).toList();
    }
}

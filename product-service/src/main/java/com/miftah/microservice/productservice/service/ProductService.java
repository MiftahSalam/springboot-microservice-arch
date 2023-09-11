package com.miftah.microservice.productservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.miftah.microservice.productservice.dto.CreateProductRequest;
import com.miftah.microservice.productservice.dto.ProductResponse;
import com.miftah.microservice.productservice.model.Product;
import com.miftah.microservice.productservice.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public void createProduct(CreateProductRequest request) {
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice()).build();

        productRepository.save(product);

        log.info("Product {} is created", product.getId());
    }

    public List<ProductResponse> getAllProduct() {
        List<Product> products = productRepository.findAll();

        return products.stream().map(this::toProductResponse).toList();
    }

    private ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .description(product.getDescription())
                .name(product.getName())
                .price(product.getPrice())
                .id(product.getId())
                .build();
    }
}

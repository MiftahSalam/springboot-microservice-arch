package com.example.miftah.microservice.inventoryservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.example.miftah.microservice.inventoryservice.model.Inventory;
import com.example.miftah.microservice.inventoryservice.repository.InventoryRepository;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryserviceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
		return args -> {
			Inventory inventory = new Inventory();
			inventory.setSku("iphone_13");
			inventory.setQuantity(100);

			Inventory inventory2 = new Inventory();
			inventory2.setSku("iphone_13_red");
			inventory2.setQuantity(0);

			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory2);
		};
	}

}

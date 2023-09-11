package com.miftah.microservice.notificationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class NotificatioServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(NotificatioServiceApp.class, args);
    }

    @KafkaListener(topics = "notificationTopic")
    public void handleNotificationPlacedOrder(OrderPlacedEvent orderPlacedEvent) {
        log.info("Received notification for order - {}", orderPlacedEvent.getOrderNumber());
    }
}
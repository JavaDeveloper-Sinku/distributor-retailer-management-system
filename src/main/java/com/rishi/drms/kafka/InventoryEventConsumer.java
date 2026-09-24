package com.rishi.drms.kafka;

import com.rishi.drms.event.OrderCreatedEvent;
import com.rishi.drms.event.OrderCreatedItemEvent;
import com.rishi.drms.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryEventConsumer {

    private final InventoryService inventoryService;

    @KafkaListener(
            topics = "order-created",
            groupId = "inventory-service-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeOrderCreated(OrderCreatedEvent event) {

        System.out.println(
                "Order received by Inventory Service: "
                        + event.getOrderNumber()
        );

        for (OrderCreatedItemEvent item : event.getItems()) {

            inventoryService.reserveStock(
                    item.getProductId(),
                    item.getQuantity()
            );
        }
    }
}
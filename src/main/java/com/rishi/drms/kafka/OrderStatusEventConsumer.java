package com.rishi.drms.kafka;

import com.rishi.drms.event.InventoryReservedEvent;
import com.rishi.drms.entity.Order;
import com.rishi.drms.enums.OrderStatus;
import com.rishi.drms.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderStatusEventConsumer {

    private final OrderRepository orderRepository;

    @KafkaListener(
            topics = "inventory-reserved",
            groupId = "order-service-group",
            containerFactory = "inventoryReservedKafkaListenerContainerFactory"
    )
    public void consumeInventoryReserved(
            InventoryReservedEvent event) {

        System.out.println(
                "Inventory reserved event received by Order Service: "
                        + event.getOrderNumber()
        );

        Order order = orderRepository
                .findById(event.getOrderId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found with id: "
                                        + event.getOrderId()
                        )
                );

        order.setStatus(OrderStatus.CONFIRMED);

        orderRepository.save(order);

        System.out.println(
                "Order confirmed successfully: "
                        + order.getOrderNumber()
        );
    }
}
package com.rishi.drms.kafka;

import com.rishi.drms.event.InventoryReservedEvent;
import com.rishi.drms.notification.dto.NotificationRequest;
import com.rishi.drms.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventConsumer {

    private final NotificationService notificationService;

    @KafkaListener(
            topics = "inventory-reserved",
            groupId = "notification-service-group",
            containerFactory =
                    "inventoryReservedKafkaListenerContainerFactory"
    )
    public void consumeInventoryReserved(
            InventoryReservedEvent event) {

        System.out.println(
                "Inventory reserved event received by Notification Service: "
                        + event.getOrderNumber()
        );

        NotificationRequest request =
                new NotificationRequest();

        request.setOrderId(event.getOrderId());
        request.setOrderNumber(event.getOrderNumber());
        request.setRetailerId(event.getRetailerId());
        request.setProductId(event.getProductId());
        request.setQuantity(event.getQuantity());
        request.setMessage(
                "Inventory reserved successfully"
        );

        notificationService.createNotification(request);
    }
}
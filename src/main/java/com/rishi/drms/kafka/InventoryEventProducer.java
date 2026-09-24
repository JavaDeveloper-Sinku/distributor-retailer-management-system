package com.rishi.drms.kafka;

import com.rishi.drms.event.InventoryReservedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryEventProducer {

    private static final String TOPIC = "inventory-reserved";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishInventoryReserved(
            InventoryReservedEvent event) {

        kafkaTemplate.send(
                TOPIC,
                event.getOrderId().toString(),
                event
        );
    }
}
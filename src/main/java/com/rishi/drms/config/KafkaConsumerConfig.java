package com.rishi.drms.config;

import com.rishi.drms.event.InventoryReservedEvent;
import com.rishi.drms.event.OrderCreatedEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    // ==========================================
    // ORDER CREATED → INVENTORY SERVICE
    // ==========================================

    @Bean
    public ConsumerFactory<String, OrderCreatedEvent> consumerFactory() {

        JsonDeserializer<OrderCreatedEvent> deserializer =
                new JsonDeserializer<>(OrderCreatedEvent.class);

        deserializer.addTrustedPackages(
                "com.rishi.drms.event"
        );

        Map<String, Object> properties = new HashMap<>();

        properties.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092"
        );

        properties.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                "inventory-service-group"
        );

        properties.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class
        );

        return new DefaultKafkaConsumerFactory<>(
                properties,
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<
            String,
            OrderCreatedEvent>
    kafkaListenerContainerFactory(
            DefaultErrorHandler errorHandler) {

        ConcurrentKafkaListenerContainerFactory<
                String,
                OrderCreatedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(
                consumerFactory()
        );

        factory.setCommonErrorHandler(
                errorHandler
        );

        return factory;
    }


    // ==========================================
    // INVENTORY RESERVED → NOTIFICATION SERVICE
    // ==========================================

    @Bean
    public ConsumerFactory<String, InventoryReservedEvent>
    inventoryReservedConsumerFactory() {

        JsonDeserializer<InventoryReservedEvent> deserializer =
                new JsonDeserializer<>(
                        InventoryReservedEvent.class
                );

        deserializer.addTrustedPackages(
                "com.rishi.drms.event"
        );

        Map<String, Object> properties = new HashMap<>();

        properties.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092"
        );

        properties.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                "notification-service-group"
        );

        properties.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class
        );

        return new DefaultKafkaConsumerFactory<>(
                properties,
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<
            String,
            InventoryReservedEvent>
    inventoryReservedKafkaListenerContainerFactory(
            DefaultErrorHandler errorHandler) {

        ConcurrentKafkaListenerContainerFactory<
                String,
                InventoryReservedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(
                inventoryReservedConsumerFactory()
        );

        factory.setCommonErrorHandler(
                errorHandler
        );

        return factory;
    }


    // ==========================================
    // KAFKA ERROR HANDLER → DEAD LETTER TOPIC
    // ==========================================

    @Bean
    public DefaultErrorHandler errorHandler(
            KafkaTemplate<String, Object> kafkaTemplate) {

        DeadLetterPublishingRecoverer recoverer =
                new DeadLetterPublishingRecoverer(
                        kafkaTemplate
                );

        /*
         * Retry configuration:
         *
         * 1st failure
         *      ↓ 1 second
         * 2nd attempt
         *      ↓ 1 second
         * 3rd attempt
         *      ↓ 1 second
         * 4th attempt
         *      ↓
         * DLT
         */
        FixedBackOff backOff =
                new FixedBackOff(
                        1000L,
                        3L
                );

        return new DefaultErrorHandler(
                recoverer,
                backOff
        );
    }
}
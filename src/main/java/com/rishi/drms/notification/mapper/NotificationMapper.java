package com.rishi.drms.notification.mapper;

import com.rishi.drms.notification.dto.NotificationRequest;
import com.rishi.drms.notification.dto.NotificationResponse;
import com.rishi.drms.notification.entity.Notification;

import java.time.LocalDateTime;

public class NotificationMapper {

    private NotificationMapper() {
    }

    public static Notification toEntity(
            NotificationRequest request) {

        return Notification.builder()
                .orderId(request.getOrderId())
                .orderNumber(request.getOrderNumber())
                .retailerId(request.getRetailerId())
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .message(request.getMessage())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static NotificationResponse toResponse(
            Notification notification) {

        return NotificationResponse.builder()
                .id(notification.getId())
                .orderId(notification.getOrderId())
                .orderNumber(notification.getOrderNumber())
                .retailerId(notification.getRetailerId())
                .productId(notification.getProductId())
                .quantity(notification.getQuantity())
                .message(notification.getMessage())
                .createdAt(notification.getCreatedAt())
                .build();
    }
}
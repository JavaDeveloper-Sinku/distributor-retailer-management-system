package com.rishi.drms.notification.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NotificationResponse {

    private Long id;

    private Long orderId;

    private String orderNumber;

    private String retailerId;

    private Long productId;

    private Integer quantity;

    private String message;

    private LocalDateTime createdAt;
}
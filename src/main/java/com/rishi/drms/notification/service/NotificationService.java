package com.rishi.drms.notification.service;

import com.rishi.drms.notification.dto.NotificationRequest;
import com.rishi.drms.notification.dto.NotificationResponse;

import java.util.List;

public interface NotificationService {

    NotificationResponse createNotification(
            NotificationRequest request
    );

    List<NotificationResponse> getAllNotifications();

    NotificationResponse getNotificationById(Long id);

    List<NotificationResponse> getNotificationsByOrderId(
            Long orderId
    );

    List<NotificationResponse> getNotificationsByRetailerId(
            String retailerId
    );

    void deleteNotification(Long id);
}
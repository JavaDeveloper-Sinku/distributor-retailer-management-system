package com.rishi.drms.notification.controller;

import com.rishi.drms.common.response.ApiResponse;
import com.rishi.drms.notification.dto.NotificationRequest;
import com.rishi.drms.notification.dto.NotificationResponse;
import com.rishi.drms.notification.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<ApiResponse<NotificationResponse>>
    createNotification(
            @Valid @RequestBody NotificationRequest request) {

        NotificationResponse response =
                notificationService.createNotification(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<NotificationResponse>builder()
                                .success(true)
                                .message("Notification created successfully")
                                .data(response)
                                .build()
                );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<NotificationResponse>>>
    getAllNotifications() {

        List<NotificationResponse> response =
                notificationService.getAllNotifications();

        return ResponseEntity.ok(
                ApiResponse.<List<NotificationResponse>>builder()
                        .success(true)
                        .message("Notifications fetched successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NotificationResponse>>
    getNotificationById(@PathVariable Long id) {

        NotificationResponse response =
                notificationService.getNotificationById(id);

        return ResponseEntity.ok(
                ApiResponse.<NotificationResponse>builder()
                        .success(true)
                        .message("Notification fetched successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>>
    getNotificationsByOrderId(
            @PathVariable Long orderId) {

        List<NotificationResponse> response =
                notificationService.getNotificationsByOrderId(
                        orderId
                );

        return ResponseEntity.ok(
                ApiResponse.<List<NotificationResponse>>builder()
                        .success(true)
                        .message(
                                "Order notifications fetched successfully"
                        )
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/retailer/{retailerId}")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>>
    getNotificationsByRetailerId(
            @PathVariable String retailerId) {

        List<NotificationResponse> response =
                notificationService.getNotificationsByRetailerId(
                        retailerId
                );

        return ResponseEntity.ok(
                ApiResponse.<List<NotificationResponse>>builder()
                        .success(true)
                        .message(
                                "Retailer notifications fetched successfully"
                        )
                        .data(response)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>>
    deleteNotification(@PathVariable Long id) {

        notificationService.deleteNotification(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Notification deleted successfully")
                        .build()
        );
    }
}
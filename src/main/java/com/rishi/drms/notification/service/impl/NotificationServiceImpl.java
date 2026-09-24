package com.rishi.drms.notification.service.impl;

import com.rishi.drms.exception.ResourceNotFoundException;
import com.rishi.drms.notification.dto.NotificationRequest;
import com.rishi.drms.notification.dto.NotificationResponse;
import com.rishi.drms.notification.entity.Notification;
import com.rishi.drms.notification.mapper.NotificationMapper;
import com.rishi.drms.notification.repository.NotificationRepository;
import com.rishi.drms.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl
        implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    @Transactional
    public NotificationResponse createNotification(
            NotificationRequest request) {

        Notification notification =
                NotificationMapper.toEntity(request);

        Notification savedNotification =
                notificationRepository.save(notification);

        return NotificationMapper.toResponse(
                savedNotification
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> getAllNotifications() {

        return notificationRepository.findAll()
                .stream()
                .map(NotificationMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public NotificationResponse getNotificationById(
            Long id) {

        Notification notification =
                notificationRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Notification not found with id: "
                                                + id
                                )
                        );

        return NotificationMapper.toResponse(notification);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> getNotificationsByOrderId(
            Long orderId) {

        return notificationRepository
                .findByOrderId(orderId)
                .stream()
                .map(NotificationMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> getNotificationsByRetailerId(
            String retailerId) {

        return notificationRepository
                .findByRetailerId(retailerId)
                .stream()
                .map(NotificationMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public void deleteNotification(Long id) {

        if (!notificationRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Notification not found with id: " + id
            );
        }

        notificationRepository.deleteById(id);
    }
}
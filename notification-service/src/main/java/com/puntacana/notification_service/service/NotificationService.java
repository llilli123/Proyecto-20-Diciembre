package com.puntacana.notification_service.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.puntacana.notification_service.dto.CreateNotificationRequest;
import com.puntacana.notification_service.model.Notification;
import com.puntacana.notification_service.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional
    public Notification createNotification(CreateNotificationRequest request) {
        Notification notification = Notification.builder()
                .title(request.getTitle())
                .message(request.getMessage())
                .type(request.getType())
                .target(request.getTarget())
                .targetReference(request.getTargetReference())
                .createdAt(LocalDateTime.now())
                .readFlag(false)
                .build();

        return notificationRepository.save(notification);
    }

    @Transactional(readOnly = true)
    public List<Notification> getAll() {
        return notificationRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Notification> getByTarget(String target) {
        return notificationRepository.findByTarget(target);
    }

    @Transactional
    public void markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found: " + id));

        notification.setReadFlag(true);
        notificationRepository.save(notification);
    }
}
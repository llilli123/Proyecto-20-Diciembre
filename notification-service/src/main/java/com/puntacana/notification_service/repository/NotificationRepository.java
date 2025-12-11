package com.puntacana.notification_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.puntacana.notification_service.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByTargetAndReadFlagFalse(String target);

    List<Notification> findByTarget(String target);
}

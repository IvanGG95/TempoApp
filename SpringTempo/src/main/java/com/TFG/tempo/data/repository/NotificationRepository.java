package com.TFG.tempo.data.repository;

import com.TFG.tempo.data.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
  Notification findByReceiverUsername(String username);

  Notification findByReceiverUserId(Long userId);

  Notification findByNotificationId(Long userId);
}

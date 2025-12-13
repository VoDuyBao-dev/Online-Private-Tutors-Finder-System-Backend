package com.example.tutorsFinderSystem.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tutorsFinderSystem.entities.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findTop5ByUserUserIdOrderByCreatedAtDesc(Long userId);
}

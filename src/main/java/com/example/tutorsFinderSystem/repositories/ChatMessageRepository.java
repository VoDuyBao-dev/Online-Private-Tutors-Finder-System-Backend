package com.example.tutorsFinderSystem.repositories;

import com.example.tutorsFinderSystem.entities.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
}

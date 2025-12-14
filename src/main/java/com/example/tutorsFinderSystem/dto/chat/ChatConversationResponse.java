package com.example.tutorsFinderSystem.dto.chat;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatConversationResponse {

    private Long userId;
    private String fullName;
    private String avatarUrl;

    private String lastMessage;
    private LocalDateTime lastTime;

    private long unreadCount;
}

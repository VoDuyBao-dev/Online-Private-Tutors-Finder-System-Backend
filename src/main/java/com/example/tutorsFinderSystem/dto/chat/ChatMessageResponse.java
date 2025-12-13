package com.example.tutorsFinderSystem.dto.chat;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChatMessageResponse {
    private Long messageId;
    private Long senderId;
    private Long receiverId;
    private String receiverEmail;
    private String content;
    private Long stickerId;
    private LocalDateTime sentAt;
}

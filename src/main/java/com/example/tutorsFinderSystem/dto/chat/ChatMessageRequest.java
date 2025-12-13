package com.example.tutorsFinderSystem.dto.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageRequest {
    private Long receiverId;
    private String content;
    private Long stickerId; // nullable
}

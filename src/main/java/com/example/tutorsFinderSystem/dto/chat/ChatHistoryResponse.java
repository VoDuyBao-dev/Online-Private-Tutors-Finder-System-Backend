package com.example.tutorsFinderSystem.dto.chat;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatHistoryResponse {

    private Long id;
    private Long senderId;
    private Long receiverId;

    private String content;
    private LocalDateTime sentAt;
}

package com.example.tutorsFinderSystem.dto.chat;

import com.example.tutorsFinderSystem.entities.ChatMessage;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageResponse {

    private Long messageId;
    private Long senderId;
    private Long receiverId;

    private String content;

    private Long stickerId;
    private String stickerUrl;

    private LocalDateTime sentAt;
    private Boolean isRead;

    /**
     * Convert Entity -> DTO
     */
    
    public static ChatMessageResponse from(ChatMessage message) {

        if (message == null)
            return null;

        return ChatMessageResponse.builder()
                .messageId(message.getMessageId())
                .senderId(message.getSender().getUserId())
                .receiverId(message.getReceiver().getUserId())
                .content(message.getContent())
                .stickerId(
                        message.getSticker() != null
                                ? message.getSticker().getStickerId()
                                : null)
                .stickerUrl(
                        message.getSticker() != null
                                ? message.getSticker().getImageUrl()
                                : null)
                .sentAt(message.getSentAt())
                .isRead(message.getIsRead())
                .build();
    }
}

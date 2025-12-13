package com.example.tutorsFinderSystem.services;

import com.example.tutorsFinderSystem.dto.chat.ChatMessageRequest;
import com.example.tutorsFinderSystem.dto.chat.ChatMessageResponse;
import com.example.tutorsFinderSystem.entities.ChatMessage;
import com.example.tutorsFinderSystem.entities.Sticker;
import com.example.tutorsFinderSystem.entities.User;
import com.example.tutorsFinderSystem.exceptions.AppException;
import com.example.tutorsFinderSystem.exceptions.ErrorCode;
import com.example.tutorsFinderSystem.repositories.ChatMessageRepository;
import com.example.tutorsFinderSystem.repositories.StickerRepository;
import com.example.tutorsFinderSystem.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final UserRepository userRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final StickerRepository stickerRepository;

    public ChatMessageResponse sendMessage(ChatMessageRequest request, String senderEmail) {

        User sender = userRepository.findByEmail(senderEmail)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        User receiver = userRepository.findById(request.getReceiverId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Sticker sticker = null;
        if (request.getStickerId() != null) {
            sticker = stickerRepository.findById(request.getStickerId())
                    .orElseThrow(() -> new AppException(ErrorCode.STICKER_NOT_FOUND));
        }

        ChatMessage message = ChatMessage.builder()
                .sender(sender)
                .receiver(receiver)
                .content(request.getContent())
                .sticker(sticker)
                .isRead(false)
                .sentAt(LocalDateTime.now())
                .build();

        chatMessageRepository.save(message);

        return ChatMessageResponse.builder()
                .messageId(message.getMessageId())
                .senderId(sender.getUserId())
                .receiverId(receiver.getUserId())
                .receiverEmail(receiver.getEmail()) // RẤT QUAN TRỌNG
                .content(message.getContent())
                .stickerId(sticker != null ? sticker.getStickerId() : null)
                .sentAt(message.getSentAt())
                .build();
    }
}

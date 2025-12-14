package com.example.tutorsFinderSystem.services;

import com.example.tutorsFinderSystem.dto.chat.ChatConversationResponse;
import com.example.tutorsFinderSystem.dto.chat.ChatHistoryResponse;
import com.example.tutorsFinderSystem.entities.ChatMessage;
import com.example.tutorsFinderSystem.entities.User;
import com.example.tutorsFinderSystem.exceptions.AppException;
import com.example.tutorsFinderSystem.exceptions.ErrorCode;
import com.example.tutorsFinderSystem.repositories.ChatMessageRepository;
import com.example.tutorsFinderSystem.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final GoogleDriveService googleDriveService;

    /**
     * Danh sách hội thoại
     */
    public List<ChatConversationResponse> getConversations(String email) {

        User me = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        List<ChatMessage> latestMessages =
                chatMessageRepository.findLatestMessagesPerConversation(me.getUserId());

        return latestMessages.stream().map(msg -> {

            User other = msg.getSender().getUserId().equals(me.getUserId())
                    ? msg.getReceiver()
                    : msg.getSender();

            long unread = chatMessageRepository
                    .countByReceiver_UserIdAndSender_UserIdAndIsReadFalse(
                            me.getUserId(),
                            other.getUserId()
                    );

            return ChatConversationResponse.builder()
                    .userId(other.getUserId())
                    .fullName(other.getFullName())
                    .avatarUrl(googleDriveService.buildAvatarUrl(other.getAvatarImage()))
                    .lastMessage(msg.getContent())
                    .lastTime(msg.getSentAt())
                    .unreadCount(unread)
                    .build();
        }).toList();
    }

    /**
     * Lịch sử chat (lazy load)
     */
    public Page<ChatHistoryResponse> getChatHistory(
            String email,
            Long otherUserId,
            int page,
            int size
    ) {

        User me = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Pageable pageable = PageRequest.of(page, size);

        Page<ChatMessage> messages =
                chatMessageRepository.findChatHistory(
                        me.getUserId(),
                        otherUserId,
                        pageable
                );

        return messages.map(msg -> ChatHistoryResponse.builder()
                .id(msg.getMessageId())
                .senderId(msg.getSender().getUserId())
                .receiverId(msg.getReceiver().getUserId())
                .content(msg.getContent())
                .sentAt(msg.getSentAt())
                .build()
        );
    }
}

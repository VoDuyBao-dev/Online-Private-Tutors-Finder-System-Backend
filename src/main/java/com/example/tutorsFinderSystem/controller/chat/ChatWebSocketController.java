package com.example.tutorsFinderSystem.controller.chat;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.example.tutorsFinderSystem.dto.chat.ChatMessageRequest;
import com.example.tutorsFinderSystem.entities.ChatMessage;
import com.example.tutorsFinderSystem.services.ChatService;
import com.example.tutorsFinderSystem.dto.chat.ChatMessageResponse;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.send")
    public void sendMessage(ChatMessageRequest request, Principal principal) {

        Long senderId = Long.valueOf(principal.getName());

        ChatMessage saved = chatService.saveMessageByUserId(senderId, request);

        ChatMessageResponse payload = ChatMessageResponse.from(saved);

        // gửi cho người nhận
        messagingTemplate.convertAndSendToUser(
                saved.getReceiver().getUserId().toString(),
                "/queue/messages",
                payload);

        // gửi lại cho người gửi (để sync)
        messagingTemplate.convertAndSendToUser(
                saved.getSender().getUserId().toString(),
                "/queue/messages",
                payload);
    }

}

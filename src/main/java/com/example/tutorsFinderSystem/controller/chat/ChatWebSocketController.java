package com.example.tutorsFinderSystem.controller.chat;

import com.example.tutorsFinderSystem.dto.chat.ChatMessageRequest;
import com.example.tutorsFinderSystem.dto.chat.ChatMessageResponse;
import com.example.tutorsFinderSystem.exceptions.AppException;
import com.example.tutorsFinderSystem.exceptions.ErrorCode;
import com.example.tutorsFinderSystem.services.ChatService;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    /**
     * Client gửi message tới: /app/chat.send
     */
    @MessageMapping("/chat.send")
    public void sendMessage(ChatMessageRequest request, Principal principal) {

        if (principal == null || principal.getName() == null) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        // principal.getName() = email (đã set từ JWT WebSocket Interceptor)
        ChatMessageResponse response = chatService.sendMessage(request, principal.getName());

        // GỬI THEO EMAIL (PHẢI KHỚP Principal.getName())
        messagingTemplate.convertAndSendToUser(
                response.getReceiverEmail(),
                "/queue/messages",
                response);
    }
}

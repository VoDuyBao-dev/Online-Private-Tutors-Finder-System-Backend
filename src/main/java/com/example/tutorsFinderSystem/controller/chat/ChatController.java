package com.example.tutorsFinderSystem.controller.chat;

import com.example.tutorsFinderSystem.dto.chat.ChatConversationResponse;
import com.example.tutorsFinderSystem.dto.chat.ChatHistoryResponse;
import com.example.tutorsFinderSystem.services.ChatService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    /**
     * Sidebar – danh sách hội thoại
     */
    @GetMapping("/conversations")
    public List<ChatConversationResponse> getConversations() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return chatService.getConversations(email);
    }

    /**
     * Lịch sử chat – lazy load
     */
    @GetMapping("/history/{userId}")
    public Page<ChatHistoryResponse> getChatHistory(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return chatService.getChatHistory(email, userId, page, size);
    }
}

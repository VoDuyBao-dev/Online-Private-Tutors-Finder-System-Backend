package com.example.tutorsFinderSystem.controller.chat;

import com.example.tutorsFinderSystem.dto.chat.ChatConversationResponse;
import com.example.tutorsFinderSystem.dto.chat.ChatHistoryResponse;
import com.example.tutorsFinderSystem.dto.chat.UserMeResponse;
import com.example.tutorsFinderSystem.entities.User;
import com.example.tutorsFinderSystem.services.ChatService;
import com.example.tutorsFinderSystem.services.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final UserService userService;

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

    @GetMapping("/me")
    public UserMeResponse me() {

        User user = userService.getCurrentUser();

        String role = SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse(null);

        return new UserMeResponse(
                user.getUserId(),
                user.getEmail(),
                user.getFullName(),
                role);
    }

}

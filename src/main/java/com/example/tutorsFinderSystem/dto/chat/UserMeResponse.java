package com.example.tutorsFinderSystem.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserMeResponse {
    private Long userId;
    private String email;
    private String fullName;
    private String role;
}
package com.example.tutorsFinderSystem.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminProfileResponse {

    private String fullName;
    private String email;
    private String phoneNumber;

    private String role;       // ADMIN
    private String roleLabel;  // Quản trị viên

    private String avatarUrl;
    private String createdAt;  // format: dd/MM/yyyy
}

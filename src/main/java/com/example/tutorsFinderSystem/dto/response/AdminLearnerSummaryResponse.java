package com.example.tutorsFinderSystem.dto.response;

import java.time.LocalDateTime;

import com.example.tutorsFinderSystem.enums.UserStatus;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminLearnerSummaryResponse {
    private Long user_id;
    private String full_name;
    private String email;
    private String phone_number;
    private LocalDateTime created_at;
    private UserStatus status;
}

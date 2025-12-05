package com.example.tutorsFinderSystem.dto.response;

import java.time.LocalDateTime;

import com.example.tutorsFinderSystem.enums.UserStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminLearnerDetailResponse {
     private Long user_id;
    private String full_name;
    private String email;
    private String phone_number;
    private String address;
    private UserStatus status;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}

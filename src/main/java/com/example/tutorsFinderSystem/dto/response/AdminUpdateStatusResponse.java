package com.example.tutorsFinderSystem.dto.response;

import com.example.tutorsFinderSystem.enums.UserStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminUpdateStatusResponse {
    private Long tutorId;
    private UserStatus newStatus;
}

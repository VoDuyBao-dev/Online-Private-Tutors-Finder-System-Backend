package com.example.tutorsFinderSystem.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LearnerProfileResponse {

    private String fullName;
    private String phoneNumber;
    private String address;

    private String email;
    // private String roleLabel;   // Người học
}

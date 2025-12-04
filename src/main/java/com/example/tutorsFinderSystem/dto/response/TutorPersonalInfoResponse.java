package com.example.tutorsFinderSystem.dto.response;

import com.example.tutorsFinderSystem.enums.Gender;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TutorPersonalInfoResponse {
    private Long tutorId;
    private Long userId;

    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
    private Gender gender;
    private String avatarImage;
}

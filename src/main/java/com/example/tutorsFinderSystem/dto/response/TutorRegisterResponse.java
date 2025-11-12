package com.example.tutorsFinderSystem.dto.response;

import com.example.tutorsFinderSystem.enums.Gender;
import com.example.tutorsFinderSystem.enums.TutorStatus;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TutorRegisterResponse {
    private Long tutorId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private Gender gender;
    private String address;
    private String university;
    private List<String> certificates;
    private String avatarUrl;
    private String proofFileUrl;
    private String educationalLevel;
    private String introduction;
    private Integer pricePerHour;
    private TutorStatus verificationStatus;
    private List<String> subjects;
    
}

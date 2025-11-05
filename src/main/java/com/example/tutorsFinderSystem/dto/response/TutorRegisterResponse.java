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

    @NotNull(message = "TUTOR_ID_REQUIRED")
    private Long tutorId;

    @NotBlank(message = "FULLNAME_REQUIRED")
    private String fullName;

    @NotBlank(message = "EMAIL_REQUIRED")
    @Email(message = "EMAIL_INVALID")
    private String email;

    @NotBlank(message = "PHONE_REQUIRED")
    private String phoneNumber;

    @NotNull(message = "GENDER_REQUIRED")
    private Gender gender;

    @NotBlank(message = "ADDRESS_REQUIRED")
    private String address;

    @NotBlank(message = "UNIVERSITY_REQUIRED")
    private String university;

    @NotEmpty(message = "CERTIFICATES_REQUIRED")
    private List<String> certificates;

    @NotBlank(message = "AVATAR_URL_REQUIRED")
    private String avatarUrl;

    @NotBlank(message = "PROOF_URL_REQUIRED")
    private String proofFileUrl;

    @NotBlank(message = "EDUCATIONAL_LEVEL_REQUIRED")
    private String educationalLevel;

    @NotBlank(message = "INTRODUCTION_REQUIRED")
    private String introduction;

    @NotNull(message = "PRICE_REQUIRED")
    @Min(value = 50000, message = "PRICE_TOO_LOW")
    @Max(value = 1000000, message = "PRICE_TOO_HIGH")
    private Integer pricePerHour;

    @NotNull(message = "VERIFICATION_STATUS_REQUIRED")
    private TutorStatus verificationStatus;

    @NotEmpty(message = "SUBJECTS_REQUIRED")
    private List<String> subjects;
}

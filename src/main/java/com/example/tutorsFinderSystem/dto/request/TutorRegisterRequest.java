package com.example.tutorsFinderSystem.dto.request;

import com.example.tutorsFinderSystem.enums.Gender;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TutorRegisterRequest {
    @NotBlank(message = "FULLNAME_REQUIRED")
    @Size(min = 2, max = 50, message = "FULLNAME_LENGTH_INVALID")
    private String fullName;

    @NotBlank(message = "EMAIL_REQUIRED")
    @Email(message = "EMAIL_INVALID")
    private String email;

    @NotBlank(message = "PASSWORD_REQUIRED")
    @Size(min = 6, max = 30, message = "PASSWORD_TOO_SHORT")
    private String password;

    @NotBlank(message = "CONFIRM_PASSWORD_REQUIRED")
    private String confirmPassword;

    @NotBlank(message = "PHONE_REQUIRED")
    @Pattern(regexp = "^(0[0-9]{9})$", message = "PHONE_INVALID")
    private String phoneNumber;

    @NotNull(message = "GENDER_REQUIRED")
    private Gender gender;

    @NotBlank(message = "ADDRESS_REQUIRED")
    @Size(max = 255, message = "ADDRESS_TOO_LONG")
    private String address;

    @NotBlank(message = "UNIVERSITY_REQUIRED")
    @Size(max = 100, message = "UNIVERSITY_TOO_LONG")
    private String university;

    @NotBlank(message = "INTRODUCTION_REQUIRED")
    private String introduction;

    @NotNull(message = "PRICE_REQUIRED")
    @Min(value = 50000, message = "PRICE_TOO_LOW")
    @Max(value = 1000000, message = "PRICE_TOO_HIGH")
    private Integer pricePerHour;

    @NotEmpty(message = "SUBJECTS_REQUIRED")
    private List<@NotNull(message = "SUBJECT_ID_REQUIRED") Long> subjectIds;

    @NotNull(message = "PROOF_URL_REQUIRED")
    private MultipartFile avatarFile; // ảnh đại diện (.jpg, .png, ...)

    private List<MultipartFile> certificateFiles;
    private List<String> certificateNames;

    @NotBlank(message = "EDUCATIONAL_LEVEL_REQUIRED")
    private String educationalLevel;
}

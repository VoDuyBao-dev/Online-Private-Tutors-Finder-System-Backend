package com.example.tutorsFinderSystem.dto.request;

import com.example.tutorsFinderSystem.enums.Gender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor  
@AllArgsConstructor
@Builder
public class TutorPersonalInfoUpdateRequest {

    @NotBlank(message = "FULL_NAME_REQUIRED")
    @Size(max = 100, message = "FULL_NAME_TOO_LONG")
    private String fullName;

    @NotBlank(message = "EMAIL_REQUIRED")
    @Email(message = "EMAIL_INVALID")
    @Size(max = 100, message = "EMAIL_TOO_LONG")
    private String email;

    @NotBlank(message = "PHONE_NUMBER_REQUIRED")
    @Pattern(
            regexp = "^(0\\d{9,10})$",
            message = "PHONE_NUMBER_INVALID"
    )
    private String phoneNumber;

    @NotNull(message = "GENDER_REQUIRED")
    private Gender gender;

    @NotBlank(message = "ADDRESS_REQUIRED")
    @Size(max = 255, message = "ADDRESS_TOO_LONG")
    private String address;
}

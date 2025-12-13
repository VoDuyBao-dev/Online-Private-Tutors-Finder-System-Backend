package com.example.tutorsFinderSystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LearnerProfileUpdateRequest {

    @NotBlank(message = "FULL_NAME_REQUIRED")
    @Size(max = 100, message = "FULL_NAME_TOO_LONG")
    private String fullName;

    @NotBlank(message = "PHONE_NUMBER_REQUIRED")
    @Pattern(regexp = "^(0\\d{9,10})$", message = "PHONE_NUMBER_INVALID")
    private String phoneNumber;

    @NotBlank(message = "ADDRESS_REQUIRED")
    @Size(max = 255, message = "ADDRESS_TOO_LONG")
    private String address;
}

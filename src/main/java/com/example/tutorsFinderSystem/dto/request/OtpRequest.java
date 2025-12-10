package com.example.tutorsFinderSystem.dto.request;

import com.example.tutorsFinderSystem.enums.OtpType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtpRequest {
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "EMAIL_INVALID"
    )
    @NotBlank(message = "EMAIL_REQUIRED")
    private String email;
    private String otpCode;
    private OtpType otpType;

}

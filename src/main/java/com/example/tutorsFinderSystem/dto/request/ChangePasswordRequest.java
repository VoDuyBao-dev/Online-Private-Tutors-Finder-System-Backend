package com.example.tutorsFinderSystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {

    @NotBlank
    private String currentPassword;

    @NotBlank
    @Size(min = 6, max = 30, message = "PASSWORD_TOO_SHORT")
    private String newPassword;

    @NotBlank
    private String confirmNewPassword;
}

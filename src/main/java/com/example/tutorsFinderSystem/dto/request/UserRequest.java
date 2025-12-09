package com.example.tutorsFinderSystem.dto.request;

import com.example.tutorsFinderSystem.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserRequest {
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "EMAIL_INVALID"
    )
    @NotBlank(message = "EMAIL_REQUIRED")
    private String email;
    private String password;
    private String confirmPassword;
    private String fullname;
    private String phone;
    private String address;
    private Set<String> roles;

}

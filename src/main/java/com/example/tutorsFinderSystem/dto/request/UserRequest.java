package com.example.tutorsFinderSystem.dto.request;

import com.example.tutorsFinderSystem.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserRequest {

    private String username;
    private String password;
    private String confirmPassword;
    private String fullname;
    private String email;
    private String phone;
    private String address;
    private Set<String> roles;

}

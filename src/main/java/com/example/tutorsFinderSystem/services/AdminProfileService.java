package com.example.tutorsFinderSystem.services;

import com.example.tutorsFinderSystem.dto.request.AdminUpdateProfileRequest;
import com.example.tutorsFinderSystem.dto.response.AdminProfileResponse;
import com.example.tutorsFinderSystem.entities.Tutor;
import com.example.tutorsFinderSystem.entities.User;
import com.example.tutorsFinderSystem.enums.Role;
import com.example.tutorsFinderSystem.exceptions.AppException;
import com.example.tutorsFinderSystem.exceptions.ErrorCode;
import com.example.tutorsFinderSystem.repositories.UserRepository;
import com.example.tutorsFinderSystem.mapper.AdminProfileMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminProfileService {

    private final UserRepository userRepository;
    private final AdminProfileMapper adminProfileMapper;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    public AdminProfileResponse updateProfile(AdminUpdateProfileRequest request) {

        User admin = getCurrentUser();

        admin.setFullName(request.getFullName());
        admin.setPhoneNumber(request.getPhoneNumber());

        userRepository.save(admin);

        return adminProfileMapper.toResponse(admin);
    }

    public AdminProfileResponse getProfile() {
        User admin = getCurrentUser();
        return adminProfileMapper.toResponse(admin);
    }
}

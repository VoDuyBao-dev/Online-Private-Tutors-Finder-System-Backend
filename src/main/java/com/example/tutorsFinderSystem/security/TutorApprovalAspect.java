package com.example.tutorsFinderSystem.security;

import com.example.tutorsFinderSystem.entities.Tutor;
import com.example.tutorsFinderSystem.enums.TutorStatus;
import com.example.tutorsFinderSystem.exceptions.AppException;
import com.example.tutorsFinderSystem.exceptions.ErrorCode;
import com.example.tutorsFinderSystem.repositories.TutorRepository;
import com.example.tutorsFinderSystem.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class TutorApprovalAspect {

    private final UserRepository userRepository;
    private final TutorRepository tutorRepository;

    @Before("@within(com.example.tutorsFinderSystem.security.RequireApprovedTutor) || " +
            "@annotation(com.example.tutorsFinderSystem.security.RequireApprovedTutor)")
    public void checkTutorApproved() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        String email = auth.getName();

        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (!user.getRole().name().equals("TUTOR")) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }
        Tutor tutor = tutorRepository.findByUserUserId(user.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.TUTOR_NOT_FOUND));

        if (tutor.getVerificationStatus() != TutorStatus.APPROVED) {
            throw new AppException(ErrorCode.TUTOR_NOT_APPROVED);
        }
    }
}

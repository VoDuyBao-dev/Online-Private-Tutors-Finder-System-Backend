package com.example.tutorsFinderSystem.repositories;

import com.example.tutorsFinderSystem.entities.OtpVerification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpVerificationRepository extends JpaRepository<OtpVerification, Long> {
    OtpVerification findTopByEmailOrderByCreatedAtDesc(String email);
    
}

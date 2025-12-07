package com.example.tutorsFinderSystem.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tutorsFinderSystem.entities.TutorCertificate;

public interface TutorCertificateRepository extends JpaRepository<TutorCertificate, Long> {

    Optional<TutorCertificate> findByTutorTutorIdAndCertificateName(Long tutorId, String string);
    
}

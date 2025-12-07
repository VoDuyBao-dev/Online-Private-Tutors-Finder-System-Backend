package com.example.tutorsFinderSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tutorsFinderSystem.entities.TutorCertificateFile;

public interface TutorCertificateFileRepository extends JpaRepository<TutorCertificateFile, Long> {
    
}

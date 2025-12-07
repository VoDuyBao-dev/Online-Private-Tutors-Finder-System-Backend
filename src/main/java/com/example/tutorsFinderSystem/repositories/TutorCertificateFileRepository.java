package com.example.tutorsFinderSystem.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tutorsFinderSystem.entities.TutorCertificateFile;

public interface TutorCertificateFileRepository extends JpaRepository<TutorCertificateFile, Long> {
    List<TutorCertificateFile> findAllByCertificate_Tutor_TutorId(Long tutorId);

}

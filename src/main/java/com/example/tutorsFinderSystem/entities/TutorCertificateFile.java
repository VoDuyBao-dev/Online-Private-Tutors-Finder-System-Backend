package com.example.tutorsFinderSystem.entities;

import java.time.LocalDateTime;

import com.example.tutorsFinderSystem.enums.CertificateStatus;
import com.example.tutorsFinderSystem.enums.TutorStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.EnumType;
import lombok.*;

@Entity
@Table(name = "tutor_certificate_files")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TutorCertificateFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;

    @ManyToOne
    @JoinColumn(name = "certificate_id", nullable = false)
    private TutorCertificate certificate;

    private String fileUrl;

    @Enumerated(EnumType.STRING)
    private CertificateStatus  status; // PENDING, APPROVED, REJECTED

    private Boolean isActive = false; // file nào đang được dùng làm minh chứng chính

    private LocalDateTime uploadedAt;

    private String adminNote;
}

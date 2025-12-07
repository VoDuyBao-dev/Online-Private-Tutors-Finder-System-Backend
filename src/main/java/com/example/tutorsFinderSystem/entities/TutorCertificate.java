package com.example.tutorsFinderSystem.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tutor_certificates")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TutorCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certificate_id")
    private Long certificateId;

    @Column(name = "certificate_name", nullable = false)
    private String certificateName;

    @Column(name = "file_url", nullable = false)
    private String fileUrl;

    @ManyToOne
    @JoinColumn(name = "tutor_id", nullable = false)
    private Tutor tutor;

}

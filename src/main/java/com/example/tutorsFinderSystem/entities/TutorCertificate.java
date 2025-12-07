package com.example.tutorsFinderSystem.entities;

import java.util.ArrayList;
import java.util.List;

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

    private Boolean approved = false;  // True khi admin duyệt ít nhất một file

    @OneToMany(mappedBy = "certificate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TutorCertificateFile> files = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "tutor_id", nullable = false)
    private Tutor tutor;

}

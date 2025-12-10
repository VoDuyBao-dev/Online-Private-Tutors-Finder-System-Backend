package com.example.tutorsFinderSystem.dto.response;

import java.time.LocalDateTime;
import com.example.tutorsFinderSystem.dto.response.TutorEducationResponse.CertificateDTO;
import java.util.List;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminTutorPendingResponse {
    private Long tutor_id;          // tutors.tutor_id
    private Long user_id;           // users.user_id
    private String full_name;       // users.full_name
    private String email;           // users.email
    private String phone_number;    // users.phone_number
    private String educational_level; // tutors.educational_level
    private LocalDateTime created_at; // users.created_at (ngày nộp)

    private List<CertificateDTO> pending_certificates;

    private List<String> subjects;  // subjects.subject_name
}

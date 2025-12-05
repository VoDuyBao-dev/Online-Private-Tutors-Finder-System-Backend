package com.example.tutorsFinderSystem.dto.response;

import java.time.LocalDateTime;

import com.example.tutorsFinderSystem.enums.Gender;
import com.example.tutorsFinderSystem.enums.UserStatus;
import lombok.*;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminTutorDetailResponse {
    private Long user_id;
    private String full_name;
    private String email;
    private String phone_number;
    private UserStatus status;
    private LocalDateTime updated_at; // ngày tham gia

    // Thông tin từ tutors
    private Long tutor_id;
    private Gender gender;
    private String address;
    private String university;
    private String educational_level;
    private String introduction;
    private Integer price_per_hour;
    private String proof_file_url;
    private String verification_status; // enum VerificationStatus trong entity Tutor (APPROVED/PENDING/REJECTED)

    // Thông tin phụ
    private List<String> subjects;      // tên môn
    private List<String> certificates;  // từ bảng tutor_certificates.certificate
    private Double average_rating;
}

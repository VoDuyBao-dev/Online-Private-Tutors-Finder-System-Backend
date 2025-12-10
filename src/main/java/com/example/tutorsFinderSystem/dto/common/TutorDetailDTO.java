package com.example.tutorsFinderSystem.dto.common;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TutorDetailDTO {
    private Long tutorId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String avatarImage;
    private String gender;
    private String address;
    private String university;
    private String educationalLevel;
    private String introduction;
    private Integer pricePerHour;
    private String verificationStatus;

    // Thông tin từ bảng User
    private String userStatus;

    // Danh sách môn dạy
    private List<SubjectDTO> subjects;

    // Danh sách chứng chỉ (chỉ lấy file đã approved)
    private List<CertificateDTO> certificates;

    // Thống kê đánh giá
    private RatingStatisticsDTO ratingStatistics;

    // Danh sách đánh giá gần nhất
    private List<RatingDTO> recentReviews;
}

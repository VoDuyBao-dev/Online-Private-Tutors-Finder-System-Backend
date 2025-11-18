package com.example.tutorsFinderSystem.dto.response;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TutorDashboardResponse {

    private TutorInfo tutorInfo;
    private Statistics stats;
    private PagedResponse<ClassItem> activeClasses;

    // thông tin cá nhân của tutor
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class TutorInfo {
        private Long tutorId;
        private String fullName;
        private String email;
        private String phoneNumber;
        private String avatarUrl;

        private String gender;
        private String address;
        private String university;
        private String educationalLevel;
        private String introduction;
        private Integer pricePerHour;

        private List<String> certificates;
        private List<String> subjects;

        private String verificationStatus;
        private Double averageRating;
    }

    // 
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Statistics { 
        private int totalSchedules;    // 
        private int newRequestCount;   // Tổng số yêu cầu mới đang chờ duyệt (PENDING)
        private int activeClassCount;  // Tổng số lớp đang dạy (CONFIRMED)
    }

    // Thông tin của một lớp mà tutor dạy 
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class ClassItem {
        private Long classId;
        private String learnerName;
        private String learnerAddress;
        private String subjectName;
        private String startDate;
        private String endDate;
    }
}

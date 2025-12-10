package com.example.tutorsFinderSystem.dto.response;

import lombok.*;
import java.util.Map;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardAdminResponse {

    private long totalLearners;
    private long totalTutors;
    private long totalClassRequests;
    private long totalEbooks;

    private Map<String, Long> ebookByType;
    private List<SubjectCountDTO> topSubjectsByTutor;
    private List<SubjectCountDTO> topRequestedSubjects;
    private Map<Integer, Long> ratingDistribution;
    private Map<String, Long> requestStatusDistribution;
    private Map<String, Long> tutorVerificationStatus;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SubjectCountDTO {
        private Long subjectId;
        private String subjectName;
        private Long count;
    }
}

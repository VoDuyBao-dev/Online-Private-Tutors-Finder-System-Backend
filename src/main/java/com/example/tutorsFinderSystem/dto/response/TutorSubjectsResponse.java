package com.example.tutorsFinderSystem.dto.response;

import java.util.List;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TutorSubjectsResponse {

    private List<SubjectItem> subjects;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SubjectItem {
        private Long subjectId;
        private String subjectName;
        
    }
}

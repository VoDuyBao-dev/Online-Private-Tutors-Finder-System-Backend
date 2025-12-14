package com.example.tutorsFinderSystem.dto.response;

import com.example.tutorsFinderSystem.enums.Gender;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TutorFilterOptionsResponse {
    // private List<String> grades;                 // lấy distinct learner.grade (để đổ UI), không filter được tutor
    private List<String> educationalLevels;      // distinct tutors.educational_level
    private List<SubjectOption> subjects;        // subjects table
    // private List<TutorOption> tutors;            // danh sách tutor APPROVED
    private List<Gender> genders;                // enum Gender

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SubjectOption {
        private Long subjectId;
        private String subjectName;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TutorOption {
        private Long tutorId;
        private String fullName;
    }
}

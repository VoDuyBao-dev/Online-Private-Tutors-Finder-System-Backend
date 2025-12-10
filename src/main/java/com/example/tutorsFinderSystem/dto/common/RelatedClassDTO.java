package com.example.tutorsFinderSystem.dto.common;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RelatedClassDTO {
    private Long classId;
    private String avatarImage;
    private String subjectName;
    private String teacherName;
    private String educationalLevel;
    private String university;
    private Integer pricePerHour;
    private String introduction;
}

package com.example.tutorsFinderSystem.dto.response;

import com.example.tutorsFinderSystem.enums.Gender;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TutorSearchItemResponse {
    private Long tutorId;
    private Long userId;
    private String fullName;

    private Gender gender;
    private String address;
    private String university;
    private String educationalLevel;
    private Integer pricePerHour;

    private String avatarUrl;           // nếu bạn có flow file/avatar
    private List<String> subjects;      // tên môn
}

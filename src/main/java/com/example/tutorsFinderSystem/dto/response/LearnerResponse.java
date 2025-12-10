package com.example.tutorsFinderSystem.dto.response;

import com.example.tutorsFinderSystem.entities.User;
import com.example.tutorsFinderSystem.enums.Gender;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LearnerResponse {
    private Long learnerId;
    private String fullName;
    private Gender gender;
    private String grade;
    private String school;
    private String description;
    private String address;
    private String addressDetail;

}

package com.example.tutorsFinderSystem.dto.common;

import com.example.tutorsFinderSystem.entities.Learner;
import com.example.tutorsFinderSystem.entities.Subject;
import com.example.tutorsFinderSystem.entities.Tutor;
import com.example.tutorsFinderSystem.enums.ClassRequestStatus;
import com.example.tutorsFinderSystem.enums.ClassRequestType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassRequestDTO {

    private Long requestId;
    private String tutor;
    private String subject;
    private LocalDate startDate;
    private LocalDate endDate;
    private String additionalNotes;
    private String status;
    private String type;
    private LocalDateTime createdAt;

}

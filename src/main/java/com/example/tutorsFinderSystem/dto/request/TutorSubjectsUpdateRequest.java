package com.example.tutorsFinderSystem.dto.request;
import lombok.*;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TutorSubjectsUpdateRequest {

    @NotEmpty(message = "SUBJECT_LIST_REQUIRED")
    private List<Long> subjectIds;
}
package com.example.tutorsFinderSystem.dto.response;

import com.example.tutorsFinderSystem.enums.TutorAvailabilityStatus;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TutorAvailabilityResponse {

    private Long availabilityId;
    private String dayOfWeek; // MONDAY, TUESDAY,...
    private String startTime; // "08:00"
    private String endTime; // "09:30"
    // private String date; // "2024-07-15"
    private String startDate; // yyyy-MM-dd
    private String endDate; // yyyy-MM-dd

    private TutorAvailabilityStatus status;
}

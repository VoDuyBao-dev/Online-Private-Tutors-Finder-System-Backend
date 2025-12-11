package com.example.tutorsFinderSystem.dto.request;

// import com.example.tutorsFinderSystem.enums.DayOfWeek;
import com.example.tutorsFinderSystem.enums.TutorAvailabilityStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TutorAvailabilityUpdateRequest {

    // @NotNull(message = "DAY_OF_WEEK_REQUIRED")
    // private DayOfWeek dayOfWeek;

    @NotBlank(message = "DATE_REQUIRED")
    private String date;

    @NotBlank(message = "START_TIME_REQUIRED")
    private String startTime;

    @NotBlank(message = "END_TIME_REQUIRED")
    private String endTime;

    @NotNull(message = "AVAILABILITY_STATUS_REQUIRED")
    private TutorAvailabilityStatus status;
}

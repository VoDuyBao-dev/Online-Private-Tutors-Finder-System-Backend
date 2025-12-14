package com.example.tutorsFinderSystem.controller.learner;

import com.example.tutorsFinderSystem.dto.ApiResponse;
import com.example.tutorsFinderSystem.dto.response.LearnerCalendarResponse;
import com.example.tutorsFinderSystem.dto.response.TutorAvailabilityResponse;
import com.example.tutorsFinderSystem.services.TutorScheduleService;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/learner/tutor-avails")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TutorAvailController {

    TutorScheduleService  tutorScheduleService;

    @GetMapping("/{tutorId}/availabilities")
    public ApiResponse<List<TutorAvailabilityResponse>> getTutorAvailableSchedule(
            @PathVariable Long tutorId,

            @RequestParam
            @NotNull(message = "START_TIME_REQUIRED")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fromDate,

            @RequestParam
            @NotNull(message = "END_TIME_REQUIRED")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate toDate
    ) {
        List<TutorAvailabilityResponse> avails = tutorScheduleService
                .getAvailableScheduleForLearner(tutorId, fromDate, toDate);

        return ApiResponse.<List<TutorAvailabilityResponse>>builder()
                .code(200)
                .message("sent trial class request successfully")
                .result(avails)
                .build();
    }
}

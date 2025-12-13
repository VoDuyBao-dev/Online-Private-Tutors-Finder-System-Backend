package com.example.tutorsFinderSystem.controller.learner;

import com.example.tutorsFinderSystem.dto.ApiResponse;
import com.example.tutorsFinderSystem.dto.response.LearnerCalendarResponse;
import com.example.tutorsFinderSystem.services.CalendarClassService;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/learner/calendar")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CalendarController {
    CalendarClassService calendarClassService;

    @GetMapping
    public ApiResponse<List<LearnerCalendarResponse>> getLearnerCalendar(
            @RequestParam("from")
            @NotNull(message = "START_TIME_REQUIRED")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate from,

            @RequestParam("to")
            @NotNull(message = "END_TIME_REQUIRED")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate to
    ) {
        return ApiResponse.<List<LearnerCalendarResponse>>builder()
                .code(200)
                .message("sent trial class request successfully")
                .result(calendarClassService.getLearnerCalendar(from, to))
                .build();
    }
}

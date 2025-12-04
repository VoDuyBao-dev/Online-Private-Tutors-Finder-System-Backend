package com.example.tutorsFinderSystem.controller.tutor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tutorsFinderSystem.dto.ApiResponse;
import com.example.tutorsFinderSystem.services.TutorDashboardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tutors/home/schedule")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('SCOPE_TUTOR')")
public class TutorHomeScheduleController {

    private final TutorDashboardService dashboardService;

    @GetMapping
    public ApiResponse<Integer> getWeeklyScheduleCount() {

        int count = dashboardService.getWeeklyScheduleCount();

        return ApiResponse.<Integer>builder()
                .code(200)
                .message("Lấy số lượng lịch dạy trong tuần thành công")
                .result(count)
                .build();
    }
}

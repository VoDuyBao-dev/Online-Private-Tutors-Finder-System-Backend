package com.example.tutorsFinderSystem.controller.tutor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tutorsFinderSystem.dto.ApiResponse;
import com.example.tutorsFinderSystem.services.TutorDashboardService;

import lombok.RequiredArgsConstructor;

    @RestController
@RequestMapping("/tutors/home/requests")
@RequiredArgsConstructor
public class TutorHomeRequestController {

    private final TutorDashboardService dashboardService;

    @GetMapping("/count")
    public ApiResponse<Integer> getNewRequestCount() {

        int count = dashboardService.getNewRequestCount();

        return ApiResponse.<Integer>builder()
                .code(200)
                .message("Lấy số lượng yêu cầu mới thành công")
                .result(count)
                .build();
    }
}

    

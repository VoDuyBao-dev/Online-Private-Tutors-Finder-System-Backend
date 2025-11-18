package com.example.tutorsFinderSystem.controller.tutor;

import com.example.tutorsFinderSystem.dto.ApiResponse;
import com.example.tutorsFinderSystem.dto.response.TutorDashboardResponse;
import com.example.tutorsFinderSystem.services.TutorDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tutors/dashboard")
@RequiredArgsConstructor
public class TutorDashboardController {

    private final TutorDashboardService dashboardService;

    @GetMapping
    public ResponseEntity<ApiResponse<TutorDashboardResponse>> getDashboard(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        TutorDashboardResponse data = dashboardService.getDashboard(page, size);

        ApiResponse<TutorDashboardResponse> response = ApiResponse.<TutorDashboardResponse>builder()
                .code(200)
                .message("Success")
                .result(data)
                .build();

        return ResponseEntity.ok(response);

    }
}

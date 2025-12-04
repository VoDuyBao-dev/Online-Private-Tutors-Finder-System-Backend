package com.example.tutorsFinderSystem.controller.tutor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tutorsFinderSystem.dto.ApiResponse;
import com.example.tutorsFinderSystem.dto.response.PagedResponse;
import com.example.tutorsFinderSystem.dto.response.TutorDashboardResponse;
import com.example.tutorsFinderSystem.services.TutorDashboardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tutors/home/classes")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('SCOPE_TUTOR')")
public class TutorHomeClassController {

   private final TutorDashboardService dashboardService;

    @GetMapping
    public ApiResponse<PagedResponse<TutorDashboardResponse.ClassItem>> getClasses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        var classes = dashboardService.getActiveClasses(page, size);

        return ApiResponse.<PagedResponse<TutorDashboardResponse.ClassItem>>builder()
                .code(200)
                .message("Lấy danh sách lớp đang dạy thành công")
                .result(classes)
                .build();
    }
}
package com.example.tutorsFinderSystem.controller.tutor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tutorsFinderSystem.dto.ApiResponse;
import com.example.tutorsFinderSystem.dto.response.TutorDashboardResponse;
import com.example.tutorsFinderSystem.security.RequireApprovedTutor;
import com.example.tutorsFinderSystem.services.TutorDashboardService;
import com.example.tutorsFinderSystem.services.GoogleDriveService;
import org.springframework.http.MediaType;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tutors/home/info")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('SCOPE_TUTOR')")
@RequireApprovedTutor
public class TutorHomeInfoController {

    private final TutorDashboardService dashboardService;


    @GetMapping
    public ApiResponse<TutorDashboardResponse.TutorInfo> getTutorInfo() {

        var info = dashboardService.getTutorInfo();

        return ApiResponse.<TutorDashboardResponse.TutorInfo>builder()
                .code(200)
                .message("Lấy thông tin gia sư thành công")
                .result(info)
                .build();
    }

}

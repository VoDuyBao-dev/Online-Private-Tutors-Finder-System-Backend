package com.example.tutorsFinderSystem.controller.admin;

import com.example.tutorsFinderSystem.dto.ApiResponse;
import com.example.tutorsFinderSystem.dto.response.AdminTutorDetailResponse;
import com.example.tutorsFinderSystem.dto.response.AdminTutorPendingResponse;
import com.example.tutorsFinderSystem.dto.response.AdminTutorSummaryResponse;
import com.example.tutorsFinderSystem.dto.response.AdminUpdateStatusResponse;
import com.example.tutorsFinderSystem.services.AdminTutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/tutors")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminTutorController {

    private final AdminTutorService adminTutorService;

    // 1) Danh sách tutor
    @GetMapping
    public ResponseEntity<ApiResponse<List<AdminTutorSummaryResponse>>> getAllTutors() {
        List<AdminTutorSummaryResponse> result = adminTutorService.getAllTutors();

        ApiResponse<List<AdminTutorSummaryResponse>> response = ApiResponse.<List<AdminTutorSummaryResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Get tutor list successfully")
                .result(result)
                .build();

        return ResponseEntity.ok(response);
    }

    // 2) Chi tiết tutor
    @GetMapping("/{tutorId}")
    public ResponseEntity<ApiResponse<AdminTutorDetailResponse>> getTutorDetail(
            @PathVariable Long tutorId) {

        AdminTutorDetailResponse result = adminTutorService.getTutorDetail(tutorId);

        ApiResponse<AdminTutorDetailResponse> response = ApiResponse.<AdminTutorDetailResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Get tutor detail successfully")
                .result(result)
                .build();

        return ResponseEntity.ok(response);
    }

    // 3) Cập nhật trạng thái User.status của tutor
    @PatchMapping("/{tutorId}/status")
    public ResponseEntity<ApiResponse<AdminUpdateStatusResponse>> updateTutorStatus(@PathVariable Long tutorId) {

        AdminUpdateStatusResponse result = adminTutorService.updateTutorStatus(tutorId);

        ApiResponse<AdminUpdateStatusResponse> response = ApiResponse.<AdminUpdateStatusResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Update tutor status successfully")
                .result(result)
                .build();

        return ResponseEntity.ok(response);
    }

    // 4) Danh sách tutor đang chờ duyệt
    @GetMapping("/pending")
    public ResponseEntity<ApiResponse<List<AdminTutorPendingResponse>>> getPendingTutors() {
        List<AdminTutorPendingResponse> result = adminTutorService.getPendingTutorApplications();

        ApiResponse<List<AdminTutorPendingResponse>> response = ApiResponse.<List<AdminTutorPendingResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Get pending tutor applications successfully")
                .result(result)
                .build();

        return ResponseEntity.ok(response);
    }

}

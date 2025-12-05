package com.example.tutorsFinderSystem.controller.admin;

import com.example.tutorsFinderSystem.dto.ApiResponse;
import com.example.tutorsFinderSystem.dto.PageResponse;
import com.example.tutorsFinderSystem.dto.response.*;
import com.example.tutorsFinderSystem.services.AdminLearnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/learners")
@RequiredArgsConstructor
public class AdminLearnerController {

    private final AdminLearnerService learnerService;

    // 1) Danh sách learner phân trang
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<AdminLearnerSummaryResponse>>> getLearners(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        var result = learnerService.getLearners(page, size);

        return ResponseEntity.ok(
                ApiResponse.<PageResponse<AdminLearnerSummaryResponse>>builder()
                        .code(200)
                        .message("Get learners successfully")
                        .result(result)
                        .build());
    }

    // 2) Chi tiết learner
    @GetMapping("/{learnerId}")
    public ResponseEntity<ApiResponse<AdminLearnerDetailResponse>> getLearnerDetail(
            @PathVariable Long learnerId) {
        var result = learnerService.getLearnerDetail(learnerId);

        return ResponseEntity.ok(
                ApiResponse.<AdminLearnerDetailResponse>builder()
                        .code(200)
                        .message("Get learner detail successfully")
                        .result(result)
                        .build());
    }

    // 3) Toggle trạng thái
    @PatchMapping("/{learnerId}/status")
    public ResponseEntity<ApiResponse<AdminLearnerStatusUpdateResponse>> toggleStatus(
            @PathVariable Long learnerId) {
        var result = learnerService.toggleStatus(learnerId);

        return ResponseEntity.ok(
                ApiResponse.<AdminLearnerStatusUpdateResponse>builder()
                        .code(200)
                        .message("Learner status updated successfully")
                        .result(result)
                        .build());
    }

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<AdminLearnerStatsResponse>> getLearnerStats() {

        AdminLearnerStatsResponse stats = learnerService.getLearnerStats();

        ApiResponse<AdminLearnerStatsResponse> response = ApiResponse.<AdminLearnerStatsResponse>builder()
                .code(200)
                .message("Get learner stats successfully")
                .result(stats)
                .build();

        return ResponseEntity.ok(response);
    }

}

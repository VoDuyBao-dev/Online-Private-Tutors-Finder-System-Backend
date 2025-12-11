package com.example.tutorsFinderSystem.controller.tutor;

import com.example.tutorsFinderSystem.dto.ApiResponse;
import com.example.tutorsFinderSystem.dto.request.TutorAvailabilityCreateRequest;
import com.example.tutorsFinderSystem.dto.request.TutorAvailabilityUpdateRequest;
import com.example.tutorsFinderSystem.dto.response.TutorAvailabilityResponse;
import com.example.tutorsFinderSystem.services.TutorScheduleService;
import com.example.tutorsFinderSystem.security.RequireApprovedTutor;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tutors/schedule")
@RequiredArgsConstructor
@PreAuthorize("hasRole('TUTOR')")
@RequireApprovedTutor
public class TutorScheduleController {

    private final TutorScheduleService tutorScheduleService;

    // Lịch rảnh của bạn
    @GetMapping("/availability")
    public ResponseEntity<ApiResponse<List<TutorAvailabilityResponse>>> getMyAvailability() {
        List<TutorAvailabilityResponse> result = tutorScheduleService.getMyAvailability();

        ApiResponse<List<TutorAvailabilityResponse>> response = ApiResponse.<List<TutorAvailabilityResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Success")
                .result(result)
                .build();

        return ResponseEntity.ok(response);
    }

    // Thêm lịch rảnh
    @PostMapping("/availability")
    public ResponseEntity<ApiResponse<TutorAvailabilityResponse>> createAvailability(
            @Valid @RequestBody TutorAvailabilityCreateRequest request) {

        TutorAvailabilityResponse result = tutorScheduleService.createAvailability(request);

        ApiResponse<TutorAvailabilityResponse> response = ApiResponse.<TutorAvailabilityResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Created")
                .result(result)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Chỉnh sửa lịch rảnh
    @PutMapping("/availability/{id}")
    public ResponseEntity<ApiResponse<TutorAvailabilityResponse>> updateAvailability(
            @PathVariable Long id,
            @Valid @RequestBody TutorAvailabilityUpdateRequest request) {

        TutorAvailabilityResponse result = tutorScheduleService.updateAvailability(id, request);

        ApiResponse<TutorAvailabilityResponse> response = ApiResponse.<TutorAvailabilityResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Updated")
                .result(result)
                .build();

        return ResponseEntity.ok(response);
    }

    // Xoá lịch rảnh
    @DeleteMapping("/availability/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAvailability(@PathVariable Long id) {
        tutorScheduleService.deleteAvailability(id);

        ApiResponse<Void> response = ApiResponse.<Void>builder()
            .code(HttpStatus.OK.value())
            .message("xóa thành công")
            .result(null)
            .build();

        return ResponseEntity.ok(response);

    }
}

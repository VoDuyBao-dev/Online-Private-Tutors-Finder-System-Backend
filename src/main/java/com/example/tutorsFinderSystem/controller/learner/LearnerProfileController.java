package com.example.tutorsFinderSystem.controller.learner;

import com.example.tutorsFinderSystem.dto.ApiResponse;
import com.example.tutorsFinderSystem.dto.request.LearnerProfileUpdateRequest;
import com.example.tutorsFinderSystem.dto.response.LearnerProfileResponse;
import com.example.tutorsFinderSystem.services.LearnerProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/learner/profile")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('SCOPE_LEARNER')")
public class LearnerProfileController {

    private final LearnerProfileService learnerProfileService;

    @GetMapping
    public ResponseEntity<ApiResponse<LearnerProfileResponse>> getProfile() {
        return ResponseEntity.ok(
                ApiResponse.<LearnerProfileResponse>builder()
                        .code(200)
                        .message("Lấy thông tin người học thành công")
                        .result(learnerProfileService.getProfile())
                        .build()
        );
    }

    @PutMapping
    public ResponseEntity<ApiResponse<LearnerProfileResponse>> updateProfile(
            @Valid @RequestBody LearnerProfileUpdateRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.<LearnerProfileResponse>builder()
                        .code(200)
                        .message("Cập nhật thông tin người học thành công")
                        .result(learnerProfileService.updateProfile(request))
                        .build()
        );
    }
}

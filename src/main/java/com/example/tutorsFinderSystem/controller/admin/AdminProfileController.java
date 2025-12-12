package com.example.tutorsFinderSystem.controller.admin;

import com.example.tutorsFinderSystem.dto.ApiResponse;
import com.example.tutorsFinderSystem.dto.request.AdminUpdateProfileRequest;
import com.example.tutorsFinderSystem.dto.response.AdminProfileResponse;
import com.example.tutorsFinderSystem.services.AdminProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/profile")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminProfileController {

    private final AdminProfileService adminProfileService;

    @GetMapping
    public ResponseEntity<ApiResponse<AdminProfileResponse>> getProfile() {
        return ResponseEntity.ok(
                ApiResponse.<AdminProfileResponse>builder()
                        .code(200)
                        .message("Lấy thông tin admin thành công")
                        .result(adminProfileService.getProfile())
                        .build()
        );
    }

    @PutMapping
    public ResponseEntity<ApiResponse<AdminProfileResponse>> updateProfile(
            @Valid @RequestBody AdminUpdateProfileRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.<AdminProfileResponse>builder()
                        .code(200)
                        .message("Cập nhật thông tin thành công")
                        .result(adminProfileService.updateProfile(request))
                        .build()
        );
    }
}

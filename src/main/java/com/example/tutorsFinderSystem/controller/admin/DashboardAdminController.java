package com.example.tutorsFinderSystem.controller.admin;
import com.example.tutorsFinderSystem.dto.response.DashboardAdminResponse;
import com.example.tutorsFinderSystem.dto.ApiResponse;
import com.example.tutorsFinderSystem.services.DashboardAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/dashboard")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class DashboardAdminController {

    private final DashboardAdminService dashboardService;

    @GetMapping
    public ResponseEntity<ApiResponse<DashboardAdminResponse>> getData() {
        return ResponseEntity.ok(
            ApiResponse.<DashboardAdminResponse>builder()
                .code(200)
                .message("Success")
                .result(dashboardService.getDashboard())
                .build()
        );
    }
}

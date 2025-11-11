package com.example.tutorsFinderSystem.controller.admin;

import com.example.tutorsFinderSystem.dto.ApiResponse;
import com.example.tutorsFinderSystem.dto.request.OtpRequest;
import com.example.tutorsFinderSystem.dto.response.OtpResponse;
import com.example.tutorsFinderSystem.services.OtpService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/test")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class test {
    OtpService otpService;

    @GetMapping("/users")
    public ApiResponse<String> getUsers() {
        return ApiResponse.<String>builder()
                .code(200)
                .message("Lấy danh sách người dùng thành công")
                .result("Danh sách người dùng giả định")
                .build();
    }





}

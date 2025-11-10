package com.example.tutorsFinderSystem.controller.admin;

import com.example.tutorsFinderSystem.dto.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/test")
public class test {
    @GetMapping("/users")
    public ApiResponse<String> getUsers() {
        return ApiResponse.<String>builder()
                .code(200)
                .message("Lấy danh sách người dùng thành công")
                .result("Danh sách người dùng giả định")
                .build();
    }
}

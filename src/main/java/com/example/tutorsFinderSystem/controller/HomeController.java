package com.example.tutorsFinderSystem.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tutorsFinderSystem.dto.ApiResponse;
import com.example.tutorsFinderSystem.dto.response.EbookResponse;
import com.example.tutorsFinderSystem.dto.response.FeaturedTutorResponse;
import com.example.tutorsFinderSystem.dto.response.NotificationResponse;
// import com.example.tutorsFinderSystem.dto.response.TutorAvailabilityResponse;
import com.example.tutorsFinderSystem.services.TutorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/public/home")
@RequiredArgsConstructor
public class HomeController {

    private final TutorService tutorService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<FeaturedTutorResponse>>> featuredTutors() {

        ApiResponse<List<FeaturedTutorResponse>> response = ApiResponse.<List<FeaturedTutorResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Success")
                .result(tutorService.getFeaturedTutors())
                .build();

        return ResponseEntity.ok(response);
        // return ApiResponse.success(tutorService.getFeaturedTutors());
    }

    @GetMapping("/featured-ebooks")
    public ApiResponse<List<EbookResponse>> featuredEbooks() {
        var data = tutorService.getFeaturedEbooks();

        return ApiResponse.<List<EbookResponse>>builder()
                .code(200)
                .message("Lấy e-books nổi bật thành công")
                .result(data)
                .build();
    }

    @GetMapping("/notifications")
    public ApiResponse<List<NotificationResponse>> latest() {

        var data = tutorService.getNotifications();

        return ApiResponse.<List<NotificationResponse>>builder()
                .code(200)
                .message("Lấy thông báo mới nhất thành công")
                .result(data)
                .build();
    }
}

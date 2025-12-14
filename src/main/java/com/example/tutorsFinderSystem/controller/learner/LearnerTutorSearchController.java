package com.example.tutorsFinderSystem.controller.learner;

import lombok.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.example.tutorsFinderSystem.dto.ApiResponse;
import com.example.tutorsFinderSystem.dto.response.TutorFilterOptionsResponse;
import com.example.tutorsFinderSystem.dto.response.TutorSearchItemResponse;
import com.example.tutorsFinderSystem.enums.Gender;
import com.example.tutorsFinderSystem.services.LearnerTutorSearchService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/learner/tutors")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('SCOPE_LEARNER')")
public class LearnerTutorSearchController {
    private final LearnerTutorSearchService tutorSearchService;

    // 1. Đổ dropdown tìm kiếm
    @GetMapping("/filters")
    public ResponseEntity<ApiResponse<TutorFilterOptionsResponse>> getFilters() {

        return ResponseEntity.ok(
                ApiResponse.<TutorFilterOptionsResponse>builder()
                        .code(HttpStatus.OK.value())
                        .message("Success")
                        .result(tutorSearchService.getFilterOptions())
                        .build());
    }

    // 2. Learner tìm kiếm gia sư
    @GetMapping("/search-filters")
    public ResponseEntity<ApiResponse<List<TutorSearchItemResponse>>> searchTutors(
            @RequestParam(value = "educationalLevel", required = false) String educationalLevel,
            @RequestParam(value = "subjectId", required = false) Long subjectId,
            // @RequestParam(value = "tutorId", required = false) Long tutorId,
            @RequestParam(value = "gender", required = false) Gender gender) {

        return ResponseEntity.ok(
                ApiResponse.<List<TutorSearchItemResponse>>builder()
                        .code(HttpStatus.OK.value())
                        .message("Success")
                        .result(
                                tutorSearchService.search(
                                        educationalLevel,
                                        subjectId,
                                        // tutorId,
                                        gender))
                        .build());
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<TutorSearchItemResponse>>> searchTutors(
            @RequestParam("q") String keyword) {
        return ResponseEntity.ok(
                ApiResponse.<List<TutorSearchItemResponse>>builder()
                        .code(HttpStatus.OK.value())
                        .message("Success")
                        .result(tutorSearchService.searchByKeyword(keyword))
                        .build());
    }

}

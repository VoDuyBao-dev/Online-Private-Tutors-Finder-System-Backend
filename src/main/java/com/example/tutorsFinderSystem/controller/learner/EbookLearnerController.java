package com.example.tutorsFinderSystem.controller.learner;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import com.example.tutorsFinderSystem.dto.ApiResponse;
import com.example.tutorsFinderSystem.dto.PageResponse;
import com.example.tutorsFinderSystem.dto.response.EbookResponse;
import com.example.tutorsFinderSystem.enums.EbookType;
import com.example.tutorsFinderSystem.services.EbookService;
// import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/learner/ebooks")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('SCOPE_LEARNER')")
public class EbookLearnerController {
    private final EbookService ebookService;
    // private final ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<EbookResponse>>> getAllEbooks(
            @RequestParam(value = "type", required = false) EbookType type,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PageResponse<EbookResponse> result = ebookService.getAllEbooks(type, keyword, page, size);

        ApiResponse<PageResponse<EbookResponse>> apiResponse = ApiResponse.<PageResponse<EbookResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Success")
                .result(result)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/search")
    public ApiResponse<PageResponse<EbookResponse>> searchEbooks(
            @RequestParam(required = false) Long subjectId,
            @RequestParam(required = false) EbookType type,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate createdDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<PageResponse<EbookResponse>>builder()
                .code(200)
                .message("Search ebooks success")
                .result(
                        ebookService.searchEbooks(
                                subjectId, type, createdDate, page, size))
                .build();
    }
}

package com.example.tutorsFinderSystem.controller.admin;

import com.example.tutorsFinderSystem.dto.ApiResponse;
import com.example.tutorsFinderSystem.dto.PageResponse;
import com.example.tutorsFinderSystem.dto.request.EbookCreateRequest;
import com.example.tutorsFinderSystem.dto.request.EbookUpdateRequest;
import com.example.tutorsFinderSystem.dto.response.EbookResponse;
import com.example.tutorsFinderSystem.enums.EbookType;
import com.example.tutorsFinderSystem.services.EbookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin/ebooks")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
public class EbookAdminController {

    private final EbookService ebookService;
    private final ObjectMapper objectMapper;

    // =====================================
    // CREATE EBOOK (JSON + FILE)
    // =====================================
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<EbookResponse>> createEbook(
            @RequestParam("data") String json,
            @RequestPart("ebookFile") MultipartFile ebookFile
    ) throws JsonProcessingException {

        // parse JSON -> EbookCreateRequest
        EbookCreateRequest request = objectMapper.readValue(json, EbookCreateRequest.class);

        // service xử lý metadata + upload file (ví dụ: lưu Google Drive / local)
        EbookResponse response = ebookService.createEbook(request, ebookFile);

        ApiResponse<EbookResponse> apiResponse = ApiResponse.<EbookResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Ebook created successfully")
                .result(response)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    // =====================================
    // UPDATE EBOOK (JSON + FILE OPTIONAL)
    // =====================================
    @PutMapping(value = "/{ebookId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<EbookResponse>> updateEbook(
            @PathVariable Long ebookId,
            @RequestParam("data") String json,
            @RequestPart(value = "ebookFile", required = false) MultipartFile ebookFile
    ) throws JsonProcessingException {

        // parse JSON -> EbookUpdateRequest
        EbookUpdateRequest request = objectMapper.readValue(json, EbookUpdateRequest.class);

        // ebookFile có thể null => chỉ update metadata
        EbookResponse response = ebookService.updateEbook(ebookId, request, ebookFile);

        ApiResponse<EbookResponse> apiResponse = ApiResponse.<EbookResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Ebook updated successfully")
                .result(response)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    // =====================================
    // DELETE EBOOK
    // =====================================
    @DeleteMapping("/{ebookId}")
    public ResponseEntity<ApiResponse<Void>> deleteEbook(
            @PathVariable Long ebookId) {

        ebookService.deleteEbook(ebookId);

        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .message("Ebook deleted successfully")
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    // =====================================
    // GET EBOOK BY ID
    // =====================================
    @GetMapping("/{ebookId}")
    public ResponseEntity<ApiResponse<EbookResponse>> getEbookById(
            @PathVariable Long ebookId) {

        EbookResponse response = ebookService.getEbookById(ebookId);

        ApiResponse<EbookResponse> apiResponse = ApiResponse.<EbookResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Success")
                .result(response)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    // =====================================
    // LIST EBOOKS (FILTER + PAGINATION)
    // =====================================
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<EbookResponse>>> getAllEbooks(
            @RequestParam(value = "type", required = false) EbookType type,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        PageResponse<EbookResponse> result =
                ebookService.getAllEbooks(type, keyword, page, size);

        ApiResponse<PageResponse<EbookResponse>> apiResponse =
                ApiResponse.<PageResponse<EbookResponse>>builder()
                        .code(HttpStatus.OK.value())
                        .message("Success")
                        .result(result)
                        .build();

        return ResponseEntity.ok(apiResponse);
    }
}

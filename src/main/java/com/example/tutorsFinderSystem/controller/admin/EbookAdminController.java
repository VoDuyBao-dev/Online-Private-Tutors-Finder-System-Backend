package com.example.tutorsFinderSystem.controller.admin;

import com.example.tutorsFinderSystem.dto.ApiResponse;
import com.example.tutorsFinderSystem.dto.request.EbookCreateRequest;
import com.example.tutorsFinderSystem.dto.request.EbookUpdateRequest;
import com.example.tutorsFinderSystem.dto.response.EbookResponse;
import com.example.tutorsFinderSystem.enums.EbookType;
import com.example.tutorsFinderSystem.services.EbookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/ebooks")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EbookAdminController {

    private final EbookService ebookService;

    @PostMapping
    public ResponseEntity<ApiResponse<EbookResponse>> createEbook(
            @Valid @RequestBody EbookCreateRequest request) {
        EbookResponse response = ebookService.createEbook(request);

        ApiResponse<EbookResponse> apiResponse = ApiResponse.<EbookResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Ebook created successfully")
                .result(response)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }


    @PutMapping("/{ebookId}")
    public ResponseEntity<ApiResponse<EbookResponse>> updateEebook(
            @PathVariable Long ebookId,
            @Valid @RequestBody EbookUpdateRequest request) {
        EbookResponse response = ebookService.updateEbook(ebookId, request);

        ApiResponse<EbookResponse> apiResponse = ApiResponse.<EbookResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Ebook updated successfully")
                .result(response)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{ebookId}")
    public ResponseEntity<ApiResponse<Void>> deleteEebook(
            @PathVariable Long ebookId) {
        ebookService.deleteEbook(ebookId);

        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .message("Ebook deleted successfully")
                .result(null)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    // Lấy thông tin của một e_book
    @GetMapping("/{ebookId}")
    public ResponseEntity<ApiResponse<EbookResponse>> getEebookById(
            @PathVariable Long ebookId) {
        EbookResponse response = ebookService.getEbookById(ebookId);

        ApiResponse<EbookResponse> apiResponse = ApiResponse.<EbookResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Success")
                .result(response)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    // Lấy danh sách e_book. có thể lấy theo type, title hoặc cả 2 
    @GetMapping
    public ResponseEntity<ApiResponse<List<EbookResponse>>> getAllEebooks(
            @RequestParam(value = "type", required = false) EbookType type,
            @RequestParam(value = "keyword", required = false) String keyword) {
        List<EbookResponse> responses = ebookService.getAllEbooks(type, keyword);

        ApiResponse<List<EbookResponse>> apiResponse = ApiResponse.<List<EbookResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Success") 
                .result(responses)
                .build();

        return ResponseEntity.ok(apiResponse);
    }
}

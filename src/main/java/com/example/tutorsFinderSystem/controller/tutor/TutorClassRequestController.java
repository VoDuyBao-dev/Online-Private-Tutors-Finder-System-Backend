package com.example.tutorsFinderSystem.controller.tutor;

import com.example.tutorsFinderSystem.dto.ApiResponse;
// import com.example.tutorsFinderSystem.dto.response.TutorRequestDetailResponse;
import com.example.tutorsFinderSystem.dto.response.TutorRequestStatusUpdateResponse;
import com.example.tutorsFinderSystem.dto.response.tutorRequestClassResponse;
import com.example.tutorsFinderSystem.enums.ClassRequestStatus;
import com.example.tutorsFinderSystem.enums.ClassRequestType;
import com.example.tutorsFinderSystem.services.TutorClassRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tutors/requests")
@RequiredArgsConstructor
public class TutorClassRequestController {

    private final TutorClassRequestService tutorHomeRequestService;

    /**
     * Lấy danh sách yêu cầu của tutor
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<tutorRequestClassResponse>>> getRequests(
            @AuthenticationPrincipal Jwt principal,
            @RequestParam(value = "status", required = false) ClassRequestStatus status,
            @RequestParam(value = "type", required = false) ClassRequestType type,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Long tutorId = extractTutorIdFromJwt(principal);

        Page<tutorRequestClassResponse> responses = tutorHomeRequestService.getRequestsForTutor(tutorId, status, type,
                keyword, page, size);

        ApiResponse<Page<tutorRequestClassResponse>> apiResponse = ApiResponse
                .<Page<tutorRequestClassResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Success")
                .result(responses)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Xem chi tiết 1 yêu cầu
     */
    // @GetMapping("/{requestId}")
    // public ResponseEntity<ApiResponse<TutorRequestDetailResponse>>
    // getRequestDetail(
    // @AuthenticationPrincipal Jwt principal,
    // @PathVariable Long requestId
    // ) {
    // Long tutorId = extractTutorIdFromJwt(principal);

    // TutorRequestDetailResponse response =
    // tutorHomeRequestService.getRequestDetail(tutorId, requestId);

    // ApiResponse<TutorRequestDetailResponse> apiResponse =
    // ApiResponse.<TutorRequestDetailResponse>builder()
    // .code(HttpStatus.OK.value())
    // .message("Success")
    // .result(response)
    // .build();

    // return ResponseEntity.ok(apiResponse);
    // }

    /**
     * Chấp nhận yêu cầu
     */
    @PatchMapping("/{requestId}/accept")
    public ResponseEntity<ApiResponse<TutorRequestStatusUpdateResponse>> acceptRequest(
            @AuthenticationPrincipal Jwt principal,
            @PathVariable Long requestId) {
        Long tutorId = extractTutorIdFromJwt(principal);

        TutorRequestStatusUpdateResponse response = tutorHomeRequestService.acceptRequest(tutorId, requestId);

        ApiResponse<TutorRequestStatusUpdateResponse> apiResponse = ApiResponse
                .<TutorRequestStatusUpdateResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Accepted")
                .result(response)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Từ chối yêu cầu
     */
    @PatchMapping("/{requestId}/reject")
    public ResponseEntity<ApiResponse<TutorRequestStatusUpdateResponse>> rejectRequest(
            @AuthenticationPrincipal Jwt principal,
            @PathVariable Long requestId) {
        Long tutorId = extractTutorIdFromJwt(principal);

        TutorRequestStatusUpdateResponse response = tutorHomeRequestService.rejectRequest(tutorId, requestId);

        ApiResponse<TutorRequestStatusUpdateResponse> apiResponse = ApiResponse
                .<TutorRequestStatusUpdateResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Rejected")
                .result(response)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    // ===== Helper: lấy tutorId từ JWT =====
    private Long extractTutorIdFromJwt(Jwt principal) {
        // [Unverified] – Tùy bạn đang lưu claim gì:
        // VD: "sub" là userId, cần map userId -> tutorId hoặc nếu JWT đã có "tutorId"
        // thì lấy trực tiếp.
        Object tutorIdClaim = principal.getClaim("tutorId");
        if (tutorIdClaim == null) {
            throw new RuntimeException("Missing tutorId in token");
        }
        return Long.valueOf(tutorIdClaim.toString());
    }
}

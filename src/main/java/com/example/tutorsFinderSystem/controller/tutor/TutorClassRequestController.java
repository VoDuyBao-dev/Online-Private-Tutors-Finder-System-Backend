package com.example.tutorsFinderSystem.controller.tutor;

import com.example.tutorsFinderSystem.dto.ApiResponse;
import com.example.tutorsFinderSystem.dto.response.TutorRequestStatusUpdateResponse;
import com.example.tutorsFinderSystem.dto.response.TutorRequestClassResponse;
import com.example.tutorsFinderSystem.enums.ClassRequestStatus;
import com.example.tutorsFinderSystem.enums.ClassRequestType;
import com.example.tutorsFinderSystem.security.RequireApprovedTutor;
import com.example.tutorsFinderSystem.services.TutorClassRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tutors/requests")
@RequiredArgsConstructor
@RequireApprovedTutor
public class TutorClassRequestController {

    private final TutorClassRequestService tutorClassRequestService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<TutorRequestClassResponse>>> getRequests(
            @RequestParam(value = "status", required = false) ClassRequestStatus status,
            @RequestParam(value = "type", required = false) ClassRequestType type,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<TutorRequestClassResponse> response = tutorClassRequestService.getRequestsForCurrentTutor(status, type,
                keyword, page, size);

        ApiResponse<Page<TutorRequestClassResponse>> api = ApiResponse
                .<Page<TutorRequestClassResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Success")
                .result(response)
                .build();

        return ResponseEntity.ok(api);
    }

    @PatchMapping("/{requestId}/accept")
    public ResponseEntity<ApiResponse<TutorRequestStatusUpdateResponse>> acceptRequest(
            @PathVariable Long requestId) {

        TutorRequestStatusUpdateResponse result = tutorClassRequestService.acceptRequestForCurrentTutor(requestId);

        return ResponseEntity.ok(
                ApiResponse.<TutorRequestStatusUpdateResponse>builder()
                        .code(HttpStatus.OK.value())
                        .message("Accepted")
                        .result(result)
                        .build());
    }

    @PatchMapping("/{requestId}/reject")
    public ResponseEntity<ApiResponse<TutorRequestStatusUpdateResponse>> rejectRequest(
            @PathVariable Long requestId) {

        TutorRequestStatusUpdateResponse result = tutorClassRequestService.rejectRequestForCurrentTutor(requestId);

        return ResponseEntity.ok(
                ApiResponse.<TutorRequestStatusUpdateResponse>builder()
                        .code(HttpStatus.OK.value())
                        .message("Rejected")
                        .result(result)
                        .build());
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

}

package com.example.tutorsFinderSystem.controller.learner;

import com.example.tutorsFinderSystem.dto.ApiResponse;
import com.example.tutorsFinderSystem.dto.common.ClassRequestDTO;
import com.example.tutorsFinderSystem.dto.request.OfficialClassRequest;
import com.example.tutorsFinderSystem.dto.request.TrialRequest;
import com.example.tutorsFinderSystem.services.ClassRequestService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/learner/class-requests")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClassRequestController {
    ClassRequestService classRequestService;

    @GetMapping
    public ApiResponse<Page<ClassRequestDTO>> getClassRequestDetails( @RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "10") int size) {
        Page<ClassRequestDTO> classRequests = classRequestService.getClassRequestDetails(page, size);
        return ApiResponse.<Page<ClassRequestDTO>>builder()
                .code(200)
                .message("Lấy danh sách yêu cầu lớp học thành công")
                .result(classRequests)
                .build();
    }

    @PostMapping("/create-trial-request")
    public ApiResponse<Void> createClassRequest(@Valid @RequestBody TrialRequest trialRequest ) {
        classRequestService.createTrialRequest(trialRequest);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("sent trial class request successfully")
                .build();
    }

    @PostMapping("/create-official-request")
    public ApiResponse<Void> createOfficialRequest(@Valid @RequestBody OfficialClassRequest officialClassRequest) {
        classRequestService.createOfficialRequest(officialClassRequest);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("sent trial class request successfully")
                .build();
    }
}

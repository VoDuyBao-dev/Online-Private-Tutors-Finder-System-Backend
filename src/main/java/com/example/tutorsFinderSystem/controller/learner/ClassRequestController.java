package com.example.tutorsFinderSystem.controller.learner;

import com.example.tutorsFinderSystem.dto.ApiResponse;
import com.example.tutorsFinderSystem.dto.common.ClassRequestDTO;
import com.example.tutorsFinderSystem.services.ClassRequestService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}

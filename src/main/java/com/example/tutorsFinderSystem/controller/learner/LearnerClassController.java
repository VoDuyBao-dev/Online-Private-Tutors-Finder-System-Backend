package com.example.tutorsFinderSystem.controller.learner;

import com.example.tutorsFinderSystem.dto.ApiResponse;
import com.example.tutorsFinderSystem.dto.response.CompletedClassResponse;
import com.example.tutorsFinderSystem.services.ClassEntityService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/learner/classes")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LearnerClassController {
    ClassEntityService classEntityService;

    @GetMapping("/getAll-classesCompleted")
    public ApiResponse<List<CompletedClassResponse>> createClassRequest() {
        List<CompletedClassResponse> classCompleteResponse = classEntityService.getCompletedClassesForLearner();
        return ApiResponse.<List<CompletedClassResponse>>builder()
                .code(200)
                .message("get completed classes successfully")
                .result(classCompleteResponse)
                .build();
    }
}

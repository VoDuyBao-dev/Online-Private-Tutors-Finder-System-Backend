package com.example.tutorsFinderSystem.controller.Classes;

import com.example.tutorsFinderSystem.dto.ApiResponse;
import com.example.tutorsFinderSystem.dto.common.RelatedClassDTO;
import com.example.tutorsFinderSystem.services.ClassEntityService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/classes")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClassController {
    ClassEntityService classEntityService;

    @GetMapping("/related")
    public ApiResponse<List<RelatedClassDTO>> getRelatedClasses(
            @RequestParam Long classId,
            @RequestParam Long subjectId,
            @RequestParam Long tutorId,
            @RequestParam(defaultValue = "6") int limit) {

        List<RelatedClassDTO> relatedClasses = classEntityService.getRelatedClasses(
                classId, subjectId, tutorId, limit
        );

        return ApiResponse.<List<RelatedClassDTO>>builder()
                        .code(200)
                        .message("Lấy danh sách lớp học liên quan thành công")
                        .result(relatedClasses)
                        .build();

    }

}

package com.example.tutorsFinderSystem.controller.tutor;

import com.example.tutorsFinderSystem.dto.ApiResponse;
import com.example.tutorsFinderSystem.dto.common.RatingDTO;
import com.example.tutorsFinderSystem.dto.common.TutorDetailDTO;
import com.example.tutorsFinderSystem.services.TutorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tutors/tutorDetail")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TutorDetailController {
    TutorService tutorService;


    @GetMapping("/{tutorId}")
    public ApiResponse<TutorDetailDTO> getTutorDetail(
            @PathVariable Long tutorId
    ) {
        TutorDetailDTO tutorDetail = tutorService.getTutorDetail(tutorId);

        return ApiResponse.<TutorDetailDTO>builder()
                        .code(200)
                        .message("Lấy thông tin gia sư thành công")
                        .result(tutorDetail)
                        .build();

    }


    @GetMapping("/{tutorId}/ratings")
    public ApiResponse<List<RatingDTO>> getAllRatings(
            @PathVariable Long tutorId
    ) {
        List<RatingDTO> ratings = tutorService.getAllRatings(tutorId);

        return ApiResponse.<List<RatingDTO>>builder()
                        .code(200)
                        .message("Lấy danh sách đánh giá thành công")
                        .result(ratings)
                        .build();

    }
}

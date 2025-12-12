package com.example.tutorsFinderSystem.controller.learner;

import com.example.tutorsFinderSystem.dto.ApiResponse;
import com.example.tutorsFinderSystem.dto.common.RatingStatisticsDTO;
import com.example.tutorsFinderSystem.dto.request.RatingRequest;
import com.example.tutorsFinderSystem.services.RatingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/learner/ratings")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RatingsController {
    RatingService ratingService;

    @PostMapping("/create-rating")
    public ApiResponse<Void> createRating(@RequestBody RatingRequest ratingRequest) {
        ratingService.createRating(ratingRequest);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Rating created successfully")
                .build();
    }
}

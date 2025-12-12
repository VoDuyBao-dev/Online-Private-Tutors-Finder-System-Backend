package com.example.tutorsFinderSystem.services;

import com.example.tutorsFinderSystem.dto.request.RatingRequest;
import com.example.tutorsFinderSystem.entities.ClassEntity;
import com.example.tutorsFinderSystem.entities.Ratings;
import com.example.tutorsFinderSystem.exceptions.AppException;
import com.example.tutorsFinderSystem.exceptions.ErrorCode;
import com.example.tutorsFinderSystem.repositories.ClassRepository;
import com.example.tutorsFinderSystem.repositories.RatingRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RatingService {
    RatingRepository ratingRepository;
    ClassRepository classRepository;

    public void createRating(RatingRequest ratingRequest) {
        if(ratingRequest.getScore()==null || ratingRequest.getScore()<1 || ratingRequest.getScore()>5){
            throw new AppException(ErrorCode.RATING_VALUE_INVALID);
        }
        ClassEntity classEntity = classRepository.findById(ratingRequest.getClassId())
                .orElseThrow(() -> new AppException(ErrorCode.CLASS_NOT_FOUND));

        Ratings rating = Ratings.builder()
                .classEntity(classEntity)
                .score(ratingRequest.getScore())
                .comment(ratingRequest.getComment())
                .build();

        ratingRepository.save(rating);

    }
}

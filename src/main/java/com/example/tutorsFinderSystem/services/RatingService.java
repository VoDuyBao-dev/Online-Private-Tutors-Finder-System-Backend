package com.example.tutorsFinderSystem.services;

import com.example.tutorsFinderSystem.dto.request.RatingRequest;
import com.example.tutorsFinderSystem.entities.ClassEntity;
import com.example.tutorsFinderSystem.entities.Learner;
import com.example.tutorsFinderSystem.entities.Ratings;
import com.example.tutorsFinderSystem.entities.User;
import com.example.tutorsFinderSystem.enums.ClassStatus;
import com.example.tutorsFinderSystem.exceptions.AppException;
import com.example.tutorsFinderSystem.exceptions.ErrorCode;
import com.example.tutorsFinderSystem.repositories.ClassRepository;
import com.example.tutorsFinderSystem.repositories.LearnerRepository;
import com.example.tutorsFinderSystem.repositories.RatingRepository;
import com.example.tutorsFinderSystem.repositories.RatingsRepository;
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
    RatingsRepository ratingRepository;
    ClassRepository classRepository;
    UserService userService;
    LearnerRepository learnerRepository;

    public void createRating(RatingRequest ratingRequest) {

        if (ratingRequest.getScore() == null
                || ratingRequest.getScore() < 1
                || ratingRequest.getScore() > 5) {
            throw new AppException(ErrorCode.RATING_VALUE_INVALID);
        }

        ClassEntity classEntity = classRepository.findById(ratingRequest.getClassId())
                .orElseThrow(() -> new AppException(ErrorCode.CLASS_NOT_FOUND));

        //Chỉ cho đánh giá khi lớp COMPLETED
        if (classEntity.getStatus() != ClassStatus.COMPLETED) {
            throw new AppException(ErrorCode.CLASS_NOT_COMPLETED);
        }

        //Check learner hiện tại có phải learner của lớp không
        User user = userService.getCurrentUser();
        Learner learner = learnerRepository
                .findByUser_Email(user.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.LEARNER_USER_NOT_FOUND));

        if (!classEntity.getClassRequest()
                .getLearner()
                .getLearnerId()
                .equals(learner.getLearnerId())) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }

        //Mỗi lớp chỉ được đánh giá 1 lần
        boolean alreadyRated = ratingRepository.existsByClassEntity_ClassId(classEntity.getClassId());

        if (alreadyRated) {
            throw new AppException(ErrorCode.CLASS_ALREADY_RATED);
        }

        Ratings rating = Ratings.builder()
                .classEntity(classEntity)
                .score(ratingRequest.getScore())
                .comment(ratingRequest.getComment())
                .build();

        ratingRepository.save(rating);
    }
}

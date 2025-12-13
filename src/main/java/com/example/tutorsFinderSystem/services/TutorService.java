package com.example.tutorsFinderSystem.services;

import com.example.tutorsFinderSystem.dto.common.RatingDTO;
import com.example.tutorsFinderSystem.dto.common.RatingStatisticsDTO;
import com.example.tutorsFinderSystem.dto.common.TutorDetailDTO;
import com.example.tutorsFinderSystem.dto.response.EbookResponse;
import com.example.tutorsFinderSystem.dto.response.NotificationResponse;
import com.example.tutorsFinderSystem.dto.response.FeaturedTutorResponse;
import com.example.tutorsFinderSystem.entities.Ratings;
import com.example.tutorsFinderSystem.entities.Tutor;
import com.example.tutorsFinderSystem.entities.User;
import com.example.tutorsFinderSystem.exceptions.AppException;
import com.example.tutorsFinderSystem.exceptions.ErrorCode;
import com.example.tutorsFinderSystem.mapper.EbookMapper;
import com.example.tutorsFinderSystem.mapper.FeaturedTutorMapper;
import com.example.tutorsFinderSystem.mapper.TutorMapper;
import com.example.tutorsFinderSystem.mapper.NotificationMapper;
import com.example.tutorsFinderSystem.repositories.EbookRepository;
import com.example.tutorsFinderSystem.repositories.NotificationRepository;
import com.example.tutorsFinderSystem.repositories.RatingsRepository;
import com.example.tutorsFinderSystem.repositories.TutorRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TutorService {

    private final TutorRepository tutorRepository;
    private final RatingsRepository ratingsRepository;
    private final TutorMapper tutorMapper;
    private final FeaturedTutorMapper featuredTutorMapper;
    private final EbookRepository ebookRepository;
    private final EbookMapper ebookMapper;
    private final NotificationRepository notificationRepository;
    private final UserService userService;
    private final NotificationMapper notificationMapper;

    @Transactional(readOnly = true)
    public TutorDetailDTO getTutorDetail(Long tutorId) {
        // Lấy thông tin tutor
        Tutor tutor = tutorRepository.findById(tutorId)
                .orElseThrow(() -> new AppException(ErrorCode.TUTOR_NOT_FOUND));

        // Tính thống kê đánh giá
        RatingStatisticsDTO statistics = calculateRatingStatistics(tutorId);

        // Lấy 5 đánh giá gần nhất
        List<Ratings> recentReviews = ratingsRepository.findRecentByTutorId(
                tutorId,
                PageRequest.of(0, 10));

        // Map sang DTO
        return tutorMapper.toTutorDetailDTO(tutor, statistics, recentReviews);
    }

    private RatingStatisticsDTO calculateRatingStatistics(Long tutorId) {
        Double avgScore = ratingsRepository.calculateAverageScore(tutorId);
        Long totalReviews = ratingsRepository.countByTutorId(tutorId);

        return RatingStatisticsDTO.builder()
                .averageScore(avgScore != null ? Math.round(avgScore * 10) / 10.0 : 0.0)
                .totalReviews(totalReviews != null ? totalReviews : 0L)
                .fiveStarCount(ratingsRepository.countByTutorIdAndScoreRange(tutorId, 4.5f, 5.1f))
                .fourStarCount(ratingsRepository.countByTutorIdAndScoreRange(tutorId, 3.5f, 4.5f))
                .threeStarCount(ratingsRepository.countByTutorIdAndScoreRange(tutorId, 2.5f, 3.5f))
                .twoStarCount(ratingsRepository.countByTutorIdAndScoreRange(tutorId, 1.5f, 2.5f))
                .oneStarCount(ratingsRepository.countByTutorIdAndScoreRange(tutorId, 0.5f, 1.5f))
                .build();
    }

    @Transactional(readOnly = true)
    public List<RatingDTO> getAllRatings(Long tutorId) {
        List<Ratings> ratings = ratingsRepository.findAllByTutorId(tutorId);
        return tutorMapper.mapReviews(ratings);
    }

    public List<FeaturedTutorResponse> getFeaturedTutors() {
        return tutorRepository.findFeaturedTutorsRaw()
                .stream()
                .map(featuredTutorMapper::toResponse)
                .toList();
    }

    public List<EbookResponse> getFeaturedEbooks() {
        return ebookRepository.findTop4ByOrderByCreatedAtDesc()
                .stream()
                .map(ebookMapper::toEbookResponse)
                .toList();
    }

    public List<NotificationResponse> getNotifications() {
        User user = userService.getCurrentUser();
        Long userId = user.getUserId();
        return notificationRepository
                .findTop5ByUserUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(notificationMapper::toResponse)
                .toList();
    }

}

package com.example.tutorsFinderSystem.services;

import com.example.tutorsFinderSystem.dto.response.*;
import com.example.tutorsFinderSystem.entities.*;
import com.example.tutorsFinderSystem.exceptions.*;
import com.example.tutorsFinderSystem.mapper.TutorDashboardMapper;
import com.example.tutorsFinderSystem.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TutorDashboardService {

	private final TutorRepository tutorRepository;
	private final ClassRequestRepository classRequestRepository;
	private final RatingRepository ratingRepository;
	private final TutorAvailabilityRepository tutorAvailabilityRepository;
	private final TutorDashboardMapper mapper;
	private final UserService userService;
	private final UserRepository userRepository;

	public TutorDashboardResponse getDashboard(int page, int size) {
		// Lấy thông tin của người dùng đang đăng nhập
		User currentUser = userService.getCurrentUser();
		Long userId = currentUser.getUserId();

		System.out.println("Current User ID: "
				+ currentUser.getUserId()
				+ " | Type: "
				+ currentUser.getUserId().getClass().getName());

		userRepository.findById(userId)
				.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

// Tìm thông tin của gia sư hiện đã đăng nhập 
		Tutor tutor = tutorRepository.findByUserUserId(userId)
				.orElseThrow(() -> new AppException(ErrorCode.TUTOR_NOT_FOUND));

		var tutorInfo = mapper.toTutorInfo(tutor);

// Đánh giá trung bình 
		Double avgRating = ratingRepository.getAverageRating(tutor.getTutorId());
		System.out.println(avgRating);
		tutorInfo.setAverageRating(avgRating == null ? 0.0 : avgRating);

// Thông tin lịch dạy 
		int scheduleCount = tutorAvailabilityRepository.countSchedules(tutor.getTutorId());
		int newRequests = classRequestRepository.countNewRequests(tutor.getTutorId());

// Phân trang cho danh sách lớp dạy 
		Pageable pageable = PageRequest.of(page, size);

		Page<ClassRequest> pageData = classRequestRepository.findActiveClasses(tutor.getTutorId(), pageable);

		var classItems = pageData.getContent()
				.stream()
				.map(mapper::toClassItem)
				.toList();

		PagedResponse<TutorDashboardResponse.ClassItem> pagedClasses = PagedResponse.of(
				classItems,
				pageData.getNumber(),
				pageData.getSize(),
				pageData.getTotalElements(),
				pageData.getTotalPages());

		return TutorDashboardResponse.builder()
				.tutorInfo(tutorInfo)
				.stats(new TutorDashboardResponse.Statistics(
						scheduleCount,
						newRequests,
						(int) pageData.getTotalElements()))
				.activeClasses(pagedClasses)
				.build();
	}
}

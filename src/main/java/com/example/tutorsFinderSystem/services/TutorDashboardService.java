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
	private final ClassRepository classRepository;
	private final RatingRepository ratingRepository;
	private final TutorAvailabilityRepository availabilityRepository;
	private final TutorDashboardMapper mapper;
	private final UserService userService;

	private Tutor getTutor() {
		Long userId = userService.getCurrentUser().getUserId();
		return tutorRepository.findByUserUserId(userId)
				.orElseThrow(() -> new AppException(ErrorCode.TUTOR_NOT_FOUND));
	}

	public TutorDashboardResponse.TutorInfo getTutorInfo() {
		Tutor tutor = getTutor();

		var info = mapper.toTutorInfo(tutor);
		Double avg = ratingRepository.getAverageRating(tutor.getTutorId());

		info.setAverageRating(avg == null ? 0 : avg);
		return info;
	}

	// public TutorDashboardResponse.Statistics getStats() {
	// Tutor tutor = getTutor();

	// int scheduleCount =
	// availabilityRepository.countSchedules(tutor.getTutorId());
	// int newRequests =
	// classRequestRepository.countNewRequests(tutor.getTutorId());
	// int totalClasses = classRepository.countOngoingClasses(tutor.getTutorId());

	// return new TutorDashboardResponse.Statistics(scheduleCount, newRequests,
	// totalClasses);
	// }

	public int getWeeklyScheduleCount() {

		Tutor tutor = getTutor();

		return availabilityRepository.countWeeklySchedules(tutor.getTutorId());
	}

	public int getNewRequestCount() {
		Tutor tutor = getTutor();
		return classRequestRepository.countNewRequests(tutor.getTutorId());
	}

	public PagedResponse<TutorDashboardResponse.ClassItem> getActiveClasses(int page, int size) {
		Tutor tutor = getTutor();

		Pageable pageable = PageRequest.of(page, size);
		Page<ClassEntity> pageData = classRepository.findOngoingClasses(tutor.getTutorId(), pageable);

		var items = pageData.getContent().stream()
				.map(mapper::toClassItem)
				.toList();

		return PagedResponse.of(
				items,
				pageData.getNumber(),
				pageData.getSize(),
				pageData.getTotalElements(),
				pageData.getTotalPages());
	}
}

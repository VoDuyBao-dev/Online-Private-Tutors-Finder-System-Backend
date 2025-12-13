package com.example.tutorsFinderSystem.services;

import com.example.tutorsFinderSystem.dto.response.TutorFilterOptionsResponse;
import com.example.tutorsFinderSystem.dto.response.TutorSearchItemResponse;
import com.example.tutorsFinderSystem.entities.Subject;
import com.example.tutorsFinderSystem.entities.Tutor;
import com.example.tutorsFinderSystem.enums.Gender;
import com.example.tutorsFinderSystem.enums.TutorStatus;
// import com.example.tutorsFinderSystem.enums.TutorStatus;
import com.example.tutorsFinderSystem.mapper.LearnerTutorSearchMapper;
// import com.example.tutorsFinderSystem.repositories.LearnerRepository;
import com.example.tutorsFinderSystem.repositories.SubjectRepository;
import com.example.tutorsFinderSystem.repositories.TutorRepository;
import com.example.tutorsFinderSystem.repositories.TutorSpecifications;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Join;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.Predicate;


import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LearnerTutorSearchService {

    private final TutorRepository tutorRepository;
    private final SubjectRepository subjectRepository;
    // private final LearnerRepository learnerRepository;
    private final LearnerTutorSearchMapper learnerTutorSearchMapper;

    @Transactional(readOnly = true)
    public TutorFilterOptionsResponse getFilterOptions() {

        // List<String> grades = learnerRepository.findDistinctGrades(); // chỉ để đổ
        // dropdown
        List<String> educationalLevels = tutorRepository.findAll().stream()
                .map(Tutor::getEducationalLevel)
                .filter(s -> s != null && !s.isBlank())
                .distinct()
                .sorted()
                .toList();

        // List<Tutor> approvedTutors =
        // tutorRepository.findByVerificationStatus(TutorStatus.APPROVED);

        List<Subject> subjects = subjectRepository.findAll();

        return TutorFilterOptionsResponse.builder()
                // .grades(grades)
                .educationalLevels(educationalLevels)
                .subjects(subjects.stream()
                        .map(s -> TutorFilterOptionsResponse.SubjectOption.builder()
                                .subjectId(s.getSubjectId())
                                .subjectName(s.getSubjectName())
                                .build())
                        .toList())
                // .tutors(approvedTutors.stream()
                // .map(t -> TutorFilterOptionsResponse.TutorOption.builder()
                // .tutorId(t.getTutorId())
                // .fullName(t.getUser() != null ? t.getUser().getFullName() : null)
                // .build())
                // .toList())
                .genders(List.of(Gender.MALE, Gender.FEMALE))
                .build();
    }

    @Transactional(readOnly = true)
    public List<TutorSearchItemResponse> search(
            String educationalLevel,
            Long subjectId,
            Gender gender) {
        Specification<Tutor> spec = (root, query, cb) -> {
            query.distinct(true);

            List<Predicate> predicates = new ArrayList<>();

            // 1. Chỉ tutor đã duyệt
            predicates.add(
                    cb.equal(root.get("verificationStatus"), TutorStatus.APPROVED));

            // 2. Trình độ (EQUAL + TRIM)
            if (educationalLevel != null && !educationalLevel.isBlank()) {
                predicates.add(
                        cb.equal(
                                cb.trim(cb.lower(root.get("educationalLevel"))),
                                educationalLevel.trim().toLowerCase()));
            }

            // 3. Giới tính
            if (gender != null) {
                predicates.add(
                        cb.equal(root.get("gender"), gender));
            }

            // 4. Môn học (JOIN 1 LẦN DUY NHẤT)
            if (subjectId != null) {
                Join<Tutor, Subject> subjectJoin = root.join("subjects", JoinType.INNER);

                predicates.add(
                        cb.equal(subjectJoin.get("subjectId"), subjectId));
            }

            return cb.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        };

        return learnerTutorSearchMapper.toItems(
                tutorRepository.findAll(spec));
    }

    public List<TutorSearchItemResponse> searchByKeyword(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            return List.of();
        }

        Specification<Tutor> spec = TutorSpecifications.approvedOnly()
                .and(TutorSpecifications.keywordSearch(keyword));

        return learnerTutorSearchMapper.toItems(
                tutorRepository.findAll(spec));
    }

}

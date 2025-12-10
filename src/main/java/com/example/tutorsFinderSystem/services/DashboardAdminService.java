package com.example.tutorsFinderSystem.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.tutorsFinderSystem.dto.response.DashboardAdminResponse;
import com.example.tutorsFinderSystem.repositories.ClassRequestRepository;
import com.example.tutorsFinderSystem.repositories.EbookRepository;
import com.example.tutorsFinderSystem.repositories.LearnerRepository;
import com.example.tutorsFinderSystem.repositories.RatingRepository;
import com.example.tutorsFinderSystem.repositories.TutorRepository;
import com.example.tutorsFinderSystem.entities.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardAdminService {

    private final LearnerRepository learnerRepo;
    private final TutorRepository tutorRepo;
    private final ClassRequestRepository classRequestRepo;
    private final EbookRepository ebookRepo;
    private final RatingRepository ratingRepo;

    public DashboardAdminResponse getDashboard() {

        long totalLearners = learnerRepo.count();
        long totalTutors = tutorRepo.count();
        long totalClassRequests = classRequestRepo.count();
        long totalEbooks = ebookRepo.count();

        // Lấy dữ liệu thô
        List<ClassRequest> requests = classRequestRepo.findAll();
        List<Ebook> ebooks = ebookRepo.findAll();
        List<Tutor> tutors = tutorRepo.findAll();
        List<Ratings> ratings = ratingRepo.findAll();

        // Thống kê ebook theo type
        Map<String, Long> ebookByType = ebooks.stream()
                .collect(Collectors.groupingBy(
                        e -> e.getType().name(),
                        Collectors.counting()
                ));

        // Top môn có nhiều tutor dạy nhất
        List<DashboardAdminResponse.SubjectCountDTO> topSubjectsByTutor =
                tutors.stream()
                        .flatMap(t -> t.getSubjects().stream())
                        .collect(Collectors.groupingBy(
                                Subject::getSubjectId,
                                Collectors.counting()
                        ))
                        .entrySet().stream()
                        .map(e -> {
                            Long id = e.getKey();
                            Long count = e.getValue();
                            String name = tutors.stream()
                                    .flatMap(t -> t.getSubjects().stream())
                                    .filter(s -> s.getSubjectId().equals(id))
                                    .findFirst()
                                    .map(Subject::getSubjectName)
                                    .orElse("Unknown");

                            return DashboardAdminResponse.SubjectCountDTO.builder()
                                    .subjectId(id)
                                    .subjectName(name)
                                    .count(count)
                                    .build();
                        })
                        .sorted((a, b) -> Long.compare(b.getCount(), a.getCount()))
                        .limit(5)
                        .toList();

        // Top môn được yêu cầu nhiều nhất
        List<DashboardAdminResponse.SubjectCountDTO> topRequestedSubjects =
                requests.stream()
                        .collect(Collectors.groupingBy(
                                cr -> cr.getSubject().getSubjectId(),
                                Collectors.counting()
                        ))
                        .entrySet().stream()
                        .map(e -> {
                            Long id = e.getKey();
                            Long count = e.getValue();
                            String name = requests.stream()
                                    .filter(r -> r.getSubject().getSubjectId().equals(id))
                                    .findFirst()
                                    .map(r -> r.getSubject().getSubjectName())
                                    .orElse("Unknown");

                            return DashboardAdminResponse.SubjectCountDTO.builder()
                                    .subjectId(id)
                                    .subjectName(name)
                                    .count(count)
                                    .build();
                        })
                        .sorted((a, b) -> Long.compare(b.getCount(), a.getCount()))
                        .limit(5)
                        .toList();

        // Rating distribution
        Map<Integer, Long> ratingDistribution = ratings.stream()
                .collect(Collectors.groupingBy(
                        r -> (int) Math.round(r.getScore()),
                        Collectors.counting()
                ));

        // Request status distribution
        Map<String, Long> requestStatusDistribution = requests.stream()
                .collect(Collectors.groupingBy(
                        r -> r.getStatus().name(),
                        Collectors.counting()
                ));

        // Tutor verification
        Map<String, Long> tutorVerificationStatus = tutors.stream()
                .collect(Collectors.groupingBy(
                        t -> t.getVerificationStatus().name(),
                        Collectors.counting()
                ));

        return DashboardAdminResponse.builder()
                .totalLearners(totalLearners)
                .totalTutors(totalTutors)
                .totalClassRequests(totalClassRequests)
                .totalEbooks(totalEbooks)
                .ebookByType(ebookByType)
                .topSubjectsByTutor(topSubjectsByTutor)
                .topRequestedSubjects(topRequestedSubjects)
                .ratingDistribution(ratingDistribution)
                .requestStatusDistribution(requestStatusDistribution)
                .tutorVerificationStatus(tutorVerificationStatus)
                .build();
    }
}

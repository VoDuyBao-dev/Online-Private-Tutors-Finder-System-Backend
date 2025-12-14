package com.example.tutorsFinderSystem.services;

import com.example.tutorsFinderSystem.dto.PageResponse;
import com.example.tutorsFinderSystem.dto.response.AdminDetailPendingResponse;
import com.example.tutorsFinderSystem.dto.response.AdminTutorDetailResponse;
import com.example.tutorsFinderSystem.dto.response.AdminTutorPendingResponse;
import com.example.tutorsFinderSystem.dto.response.AdminTutorStatusUpdateResponse;
import com.example.tutorsFinderSystem.dto.response.AdminTutorSummaryResponse;
import com.example.tutorsFinderSystem.entities.Subject;
import com.example.tutorsFinderSystem.entities.Tutor;
import com.example.tutorsFinderSystem.entities.TutorCertificateFile;
import com.example.tutorsFinderSystem.entities.User;
import com.example.tutorsFinderSystem.enums.CertificateStatus;
import com.example.tutorsFinderSystem.enums.Role;
import com.example.tutorsFinderSystem.enums.TutorStatus;
// import com.example.tutorsFinderSystem.enums.TutorStatus;
import com.example.tutorsFinderSystem.enums.UserStatus;
import com.example.tutorsFinderSystem.exceptions.AppException;
import com.example.tutorsFinderSystem.exceptions.ErrorCode;
import com.example.tutorsFinderSystem.mapper.AdminTutorMapper;
import com.example.tutorsFinderSystem.repositories.RatingRepository;
import com.example.tutorsFinderSystem.repositories.TutorCertificateFileRepository;
import com.example.tutorsFinderSystem.repositories.TutorRepository;
import com.example.tutorsFinderSystem.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.math.RoundingMode;
// import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminTutorService {

    private final TutorRepository tutorRepository;
    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;
    private final TutorCertificateFileRepository tutorCertificateFileRepository;
    private final AdminTutorMapper adminTutorMapper;

    // 1) Danh sách tutor cho admin
    @Transactional
    public PageResponse<AdminTutorSummaryResponse> getAllTutors(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("user.updatedAt").descending());

        Page<Tutor> tutorPage = tutorRepository.findAllTutorsPageable("TUTOR",pageable);

        List<AdminTutorSummaryResponse> items = tutorPage.getContent().stream()
                .map(tutor -> {
                    Double avg = ratingRepository.getAverageRating(tutor.getTutorId());
                    Double rounded = roundOneDecimal(avg);

                    List<String> subjects = tutor.getSubjects().stream()
                            .map(Subject::getSubjectName)
                            .sorted()
                            .toList();

                    return adminTutorMapper.toSummaryResponse(tutor, subjects, rounded);
                })
                .toList();

        return PageResponse.<AdminTutorSummaryResponse>builder()
                .items(items)
                .page(page)
                .size(size)
                .totalItems(tutorPage.getTotalElements())
                .totalPages(tutorPage.getTotalPages())
                .build();
    }

    // 2) Chi tiết tutor
    @Transactional
    public AdminTutorDetailResponse getTutorDetail(Long tutorId) {
        Tutor tutor = tutorRepository.findById(tutorId)
                .orElseThrow(() -> new AppException(ErrorCode.TUTOR_NOT_FOUND));

        User user = tutor.getUser();
        if (user == null) {
            throw new AppException(ErrorCode.TUTOR_USER_NOT_FOUND);
        }

        if (!user.getRoles().contains(Role.TUTOR.name())) {
            throw new AppException(ErrorCode.USER_IS_NOT_TUTOR);
        }

        Double avg = ratingRepository.getAverageRating(tutorId);
        Double rounded = roundOneDecimal(avg);

        List<String> subjects = tutor.getSubjects().stream()
                .map(Subject::getSubjectName)
                .sorted()
                .collect(Collectors.toList());

        // List<String> certificates =
        // tutorRepository.findCertificatesByTutorId(tutorId);

        return adminTutorMapper.toDetailResponse(tutor, subjects, rounded);
    }

    // 3) Cập nhật trạng thái tài khoản User của tutor
    @Transactional
    public AdminTutorStatusUpdateResponse updateTutorStatus(Long tutorId) {

        Tutor tutor = tutorRepository.findById(tutorId)
                .orElseThrow(() -> new AppException(ErrorCode.TUTOR_NOT_FOUND));

        User user = tutor.getUser();
        if (user == null)
            throw new AppException(ErrorCode.TUTOR_USER_NOT_FOUND);

        UserStatus current = user.getStatus();
        UserStatus next = (current == UserStatus.ACTIVE)
                ? UserStatus.INACTIVE
                : UserStatus.ACTIVE;

        user.setStatus(next);
        userRepository.save(user);

        return new AdminTutorStatusUpdateResponse(tutorId, next);
    }

    // Làm tròn điểm trung bình đến 1 chữ số thập phân
    private Double roundOneDecimal(Double value) {
        double v = (value == null) ? 0.0 : value;
        return BigDecimal.valueOf(v)
                .setScale(1, RoundingMode.HALF_UP)
                .doubleValue();
    }

    // Lấy danh sách tutor đang chờ duyệt
    @Transactional
    public PageResponse<AdminTutorPendingResponse> getPendingTutorApplications(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("user.createdAt").ascending());

        Page<Tutor> tutorPage = tutorRepository.findPendingTutors(pageable);

        List<AdminTutorPendingResponse> items = tutorPage.getContent().stream()
                .map(tutor -> {

                    List<String> subjects = tutor.getSubjects().stream()
                            .map(Subject::getSubjectName)
                            .sorted()
                            .toList();

                    return adminTutorMapper.toPendingResponse(tutor, subjects);
                })
                .toList();

        return PageResponse.<AdminTutorPendingResponse>builder()
                .items(items)
                .page(page)
                .size(size)
                .totalItems(tutorPage.getTotalElements())
                .totalPages(tutorPage.getTotalPages())
                .build();
    }

    @Transactional
    public AdminDetailPendingResponse getTutorPendingDetail(Long tutorId) {
        Tutor tutor = tutorRepository.findById(tutorId)
                .orElseThrow(() -> new AppException(ErrorCode.TUTOR_NOT_FOUND));

        User user = tutor.getUser();
        if (user == null) {
            throw new AppException(ErrorCode.TUTOR_USER_NOT_FOUND);
        }

        if (!user.getRoles().contains(Role.TUTOR.name())) {
            throw new AppException(ErrorCode.USER_IS_NOT_TUTOR);
        }

        List<String> subjects = tutor.getSubjects().stream()
                .map(Subject::getSubjectName)
                .sorted()
                .collect(Collectors.toList());

        // List<String> certificates =
        // tutorRepository.findCertificatesByTutorId(tutorId);

        return adminTutorMapper.toDetailPendingResponse(tutor, subjects);
    }

    @Transactional
    public void approveTutor(Long tutorId) {

        Tutor tutor = tutorRepository.findById(tutorId)
                .orElseThrow(() -> new AppException(ErrorCode.TUTOR_NOT_FOUND));

        User user = tutor.getUser();

        // ============================
        // 1) Cập nhật trạng thái TUTOR
        // ============================
        tutor.setVerificationStatus(TutorStatus.APPROVED);

        // ============================
        // 2) Cập nhật trạng thái USER
        // ============================
        user.setStatus(UserStatus.ACTIVE);
        user.setEnabled(true);

        // ============================
        // 3) Cập nhật CERTIFICATE FILES
        // ============================
        List<TutorCertificateFile> files = tutorCertificateFileRepository
                .findAllByCertificate_Tutor_TutorId(tutorId);

        // Không có file → bỏ qua phần này
        if (!files.isEmpty()) {

            // Tìm file mới nhất để set isActive = true
            TutorCertificateFile newestFile = files.stream()
                    .sorted((f1, f2) -> f2.getUploadedAt().compareTo(f1.getUploadedAt()))
                    .findFirst()
                    .get();

            for (TutorCertificateFile file : files) {
                file.setStatus(CertificateStatus.APPROVED);

                if (file.getFileId().equals(newestFile.getFileId())) {
                    file.setIsActive(true);
                } else {
                    file.setIsActive(false);
                }
            }

            tutorCertificateFileRepository.saveAll(files);
        }

        // ============================
        // 4) LƯU TUTOR & USER
        // ============================
        userRepository.save(user);
        tutorRepository.save(tutor);
    }

}

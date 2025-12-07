package com.example.tutorsFinderSystem.services;

import com.example.tutorsFinderSystem.dto.request.TutorEducationUpdateRequest;
import com.example.tutorsFinderSystem.dto.request.TutorPersonalInfoUpdateRequest;
import com.example.tutorsFinderSystem.dto.request.TutorSubjectsUpdateRequest;
import com.example.tutorsFinderSystem.dto.response.TutorPersonalInfoResponse;
import com.example.tutorsFinderSystem.dto.response.TutorSubjectsResponse;
import com.example.tutorsFinderSystem.dto.response.TutorAvatarUpdateResponse;
import com.example.tutorsFinderSystem.dto.response.TutorEducationResponse;
import com.example.tutorsFinderSystem.dto.response.TutorRatingsSummaryResponse;
import com.example.tutorsFinderSystem.entities.Subject;
import com.example.tutorsFinderSystem.entities.Tutor;
import com.example.tutorsFinderSystem.entities.TutorCertificate;
import com.example.tutorsFinderSystem.entities.TutorCertificateFile;
import com.example.tutorsFinderSystem.entities.User;
import com.example.tutorsFinderSystem.enums.CertificateStatus;
import com.example.tutorsFinderSystem.exceptions.AppException;
import com.example.tutorsFinderSystem.exceptions.ErrorCode;
import com.example.tutorsFinderSystem.mapper.TutorProfileMapper;
import com.example.tutorsFinderSystem.repositories.SubjectRepository;
import com.example.tutorsFinderSystem.repositories.TutorCertificateFileRepository;
import com.example.tutorsFinderSystem.repositories.TutorCertificateRepository;
import com.example.tutorsFinderSystem.repositories.TutorRepository;
import com.example.tutorsFinderSystem.repositories.UserRepository;
import com.example.tutorsFinderSystem.repositories.RatingRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

// import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class TutorProfileService {

    private final UserRepository userRepository;
    private final TutorRepository tutorRepository;
    private final SubjectRepository subjectRepository;
    private final RatingRepository ratingsRepository;
    private final TutorProfileMapper mapper;
    private final UserService userService;
    private final GoogleDriveService googleDriveService;
    private final TutorCertificateRepository tutorCertificateRepository;
    private final TutorCertificateFileRepository tutorCertificateFileRepository;


    // private static final String UPLOAD_ROOT = System.getProperty("user.dir") +
    // "/uploads/tutors/";

    // private User getCurrentUser() {
    // var email = SecurityContextHolder.getContext().getAuthentication().getName();
    // return userRepository.findByEmail(email)
    // .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    // }

    private Tutor getTutor(User user) {
        return tutorRepository.findByUserUserId(user.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.TUTOR_NOT_FOUND));
    }

    // ------------------------------
    // 1. PERSONAL INFO
    // ------------------------------

    public TutorPersonalInfoResponse getPersonalInfo() {
        User user = userService.getCurrentUser();
        Tutor tutor = getTutor(user);

        return mapper.toPersonalInfo(user, tutor);
    }

    @Transactional
    public TutorPersonalInfoResponse updatePersonalInfo(TutorPersonalInfoUpdateRequest request) {
        User user = userService.getCurrentUser();
        Tutor tutor = getTutor(user);

        user.setFullName(request.getFullName());
        user.setPhoneNumber(request.getPhoneNumber());

        tutor.setGender(request.getGender());
        tutor.setAddress(request.getAddress());

        return mapper.toPersonalInfo(userRepository.save(user), tutorRepository.save(tutor));
    }

    // ------------------------------
    // 2. EDUCATION INFO
    // ------------------------------

    public TutorEducationResponse getEducation() {
        Tutor tutor = getTutor(userService.getCurrentUser());
        return mapper.toEducation(tutor);
    }

    @Transactional
    public TutorEducationResponse updateEducation(TutorEducationUpdateRequest req) {

        Tutor tutor = getTutor(userService.getCurrentUser());

        MultipartFile proofFile = req.getProofFile();
        if (proofFile == null || proofFile.isEmpty()) {
            throw new AppException(ErrorCode.PROOF_FILE_REQUIRED);
        }

        // Chỉ nhận PDF
        if (!"application/pdf".equals(proofFile.getContentType())) {
            throw new AppException(ErrorCode.INVALID_PROOF_FILE_TYPE);
        }

        // 1) Upload file mới lên Google Drive
        String newUrl;
        try {
            newUrl = googleDriveService.upload(proofFile, "tutor_certificates");
        } catch (Exception e) {
            throw new AppException(ErrorCode.PROOF_FILE_UPLOAD_FAILED);
        }

        // 2) Tạo record chứng chỉ nếu tutor chưa có (hồ sơ học vấn chính)
        // Ở đây bạn có thể quyết định có 1 chứng chỉ hay nhiều.
        // Tôi giả sử mỗi tutor có 1 "proof" chính (như CV học vấn).
        TutorCertificate certificate = tutorCertificateRepository
                .findByTutorTutorIdAndCertificateName(tutor.getTutorId(), "EDUCATION_PROOF")
                .orElse(null);

        if (certificate == null) {
            certificate = TutorCertificate.builder()
                    .tutor(tutor)
                    .certificateName("EDUCATION_PROOF")
                    .approved(false)
                    .build();
            tutorCertificateRepository.save(certificate);
        }

        // 3) Tạo record file pending (không đụng file đã duyệt)
        TutorCertificateFile certFile = TutorCertificateFile.builder()
                .certificate(certificate)
                .fileUrl(newUrl)
                .status(CertificateStatus.PENDING)
                .isActive(false)
                .uploadedAt(LocalDateTime.now())
                .build();

        tutorCertificateFileRepository.save(certFile);

        // 4) Cập nhật thông tin khác trong hồ sơ
        tutor.setUniversity(req.getUniversity());
        tutor.setIntroduction(req.getIntroduction());
        tutor.setPricePerHour(req.getPricePerHour());
        tutorRepository.save(tutor);

        return mapper.toEducation(tutor);
    }

    // ------------------------------
    // 3. SUBJECTS
    // ------------------------------

    public TutorSubjectsResponse getSubjects() {
        Tutor tutor = getTutor(userService.getCurrentUser());
        var subjects = tutor.getSubjects()
                .stream()
                .map(s -> new TutorSubjectsResponse.SubjectItem(
                        s.getSubjectId(), s.getSubjectName()))
                .toList();

        return new TutorSubjectsResponse(subjects);
    }

    @Transactional
    public TutorSubjectsResponse updateSubjects(TutorSubjectsUpdateRequest req) {
        Tutor tutor = getTutor(userService.getCurrentUser());

        var subjectList = subjectRepository.findAllById(req.getSubjectIds());

        // tutor.setSubjects(new HashSet<>(subjectList));
        Set<Subject> newSubjects = new HashSet<>(subjectList);
        tutor.setSubjects(newSubjects);

        tutorRepository.save(tutor);

        return getSubjects();
    }

    // 4. RATINGS SUMMARY
    public TutorRatingsSummaryResponse getRatingsSummary() {
        Tutor tutor = getTutor(userService.getCurrentUser());

        Double avg = ratingsRepository.getAverageRating(tutor.getTutorId());
        Long count = ratingsRepository.getTotalReviews(tutor.getTutorId());

        return TutorRatingsSummaryResponse.builder()
                .averageRating(avg == null ? 0.0 : avg)
                .totalReviews(count == null ? 0 : count)
                .build();
    }

    @Transactional
    public TutorAvatarUpdateResponse updateAvatar(MultipartFile avatarFile) {

        User user = userService.getCurrentUser();
        Tutor tutor = getTutor(user);

        if (avatarFile == null || avatarFile.isEmpty()) {
            throw new AppException(ErrorCode.AVATAR_FILE_REQUIRED);
        }

        if (!isValidImage(avatarFile.getContentType())) {
            throw new AppException(ErrorCode.AVATAR_INVALID_TYPE);
        }

        // UPLOAD GOOGLE DRIVE — GIỐNG RegisterService
        String avatarUrl;
        try {
            avatarUrl = googleDriveService.upload(avatarFile, "avatars");
        } catch (Exception e) {
            throw new AppException(ErrorCode.AVATAR_UPLOAD_FAILED);
        }

        // LƯU DB
        user.setAvatarImage(avatarUrl);
        userRepository.save(user);

        return TutorAvatarUpdateResponse.builder()
                .avatarUrl(avatarUrl)
                .build();
    }

    private boolean isValidImage(String contentType) {
        return contentType != null &&
                (contentType.equals("image/png")
                        || contentType.equals("image/jpeg")
                        || contentType.equals("image/jpg"));
    }

}

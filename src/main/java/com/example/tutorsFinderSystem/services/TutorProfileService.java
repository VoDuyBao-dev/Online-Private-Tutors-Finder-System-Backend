package com.example.tutorsFinderSystem.services;

import com.example.tutorsFinderSystem.dto.request.ChangePasswordRequest;
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
import java.util.List;
import java.util.Set;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.example.tutorsFinderSystem.dto.response.TutorEducationResponse.TutorCertificateUpdateDTO;

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
    private final PasswordEncoder passwordEncoder;

    private Tutor getTutor(User user) {
        return tutorRepository.findByUserUserId(user.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.TUTOR_NOT_FOUND));
    }

    // 1. PERSONAL INFO

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

    // 2. EDUCATION INFO

    public TutorEducationResponse getEducation() {
        Tutor tutor = getTutor(userService.getCurrentUser());
        return mapper.toEducation(tutor);
    }

    @Transactional
    public TutorEducationResponse updateEducation(TutorEducationUpdateRequest req) {

        Tutor tutor = getTutor(userService.getCurrentUser());

        List<TutorCertificateUpdateDTO> certUpdates = req.getCertificates();
        List<MultipartFile> files = req.getCertificateFiles();

        for (int i = 0; i < certUpdates.size(); i++) {

            TutorCertificateUpdateDTO dto = certUpdates.get(i);

            TutorCertificate certificate = tutorCertificateRepository
                    .findById(dto.getCertificateId())
                    .orElseThrow(() -> new AppException(ErrorCode.CERTIFICATE_NOT_FOUND));

            // ===== 1. UPDATE TÊN CHỨNG CHỈ =====
            certificate.setCertificateName(dto.getCertificateName());
            tutorCertificateRepository.save(certificate);

            // ===== 2. XỬ LÝ FILE (NẾU CÓ) =====
            MultipartFile newFile = (files != null && files.size() > i)
                    ? files.get(i)
                    : null;

            if (newFile != null && !newFile.isEmpty()) {

                if (!"application/pdf".equals(newFile.getContentType())) {
                    throw new AppException(ErrorCode.INVALID_PROOF_FILE_TYPE);
                }

                String newUrl;
                try {
                    newUrl = googleDriveService.upload(newFile, "certificates");
                } catch (Exception e) {
                    throw new AppException(ErrorCode.PROOF_FILE_UPLOAD_FAILED);
                }

                // Tạo record file mới – KHÔNG ĐỤNG FILE CŨ
                TutorCertificateFile newCertFile = TutorCertificateFile.builder()
                        .certificate(certificate)
                        .fileUrl(newUrl)
                        .status(CertificateStatus.PENDING)
                        .isActive(false)
                        .uploadedAt(LocalDateTime.now())
                        .build();

                tutorCertificateFileRepository.save(newCertFile);
            }
        }

        // ===== 3. UPDATE THÔNG TIN KHÁC =====
        tutor.setUniversity(req.getUniversity());
        tutor.setIntroduction(req.getIntroduction());
        tutor.setPricePerHour(req.getPricePerHour());

        tutorRepository.save(tutor);

        return mapper.toEducation(tutor);
    }

    // 3. SUBJECTS

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

        // UPLOAD GOOGLE DRIVE
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

    public void changePassword(ChangePasswordRequest request) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // 1. Check mật khẩu hiện tại
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPasswordHash())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }

        // 2. Check confirm
        if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
            throw new AppException(ErrorCode.PASSWORD_CONFIRM_MISMATCH);
        }

        // 3. Không cho trùng mật khẩu cũ
        if (passwordEncoder.matches(request.getNewPassword(), user.getPasswordHash())) {
            throw new AppException(ErrorCode.NEW_PASSWORD_SAME_AS_OLD);
        }

        // 4. Update
        user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

}

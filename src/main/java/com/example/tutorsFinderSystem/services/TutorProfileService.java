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
import com.example.tutorsFinderSystem.entities.User;
import com.example.tutorsFinderSystem.exceptions.AppException;
import com.example.tutorsFinderSystem.exceptions.ErrorCode;
import com.example.tutorsFinderSystem.mapper.TutorProfileMapper;
import com.example.tutorsFinderSystem.repositories.SubjectRepository;
import com.example.tutorsFinderSystem.repositories.TutorRepository;
import com.example.tutorsFinderSystem.repositories.UserRepository;
import com.example.tutorsFinderSystem.repositories.RatingRepository;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
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
    private static final String UPLOAD_ROOT = System.getProperty("user.dir") + "/uploads/tutors/";

    private User getCurrentUser() {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    private Tutor getTutor(User user) {
        return tutorRepository.findByUserUserId(user.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.TUTOR_NOT_FOUND));
    }

    // ------------------------------
    // 1. PERSONAL INFO
    // ------------------------------

    public TutorPersonalInfoResponse getPersonalInfo() {
        User user = getCurrentUser();
        Tutor tutor = getTutor(user);
        return mapper.toPersonalInfo(user, tutor);
    }

    @Transactional
    public TutorPersonalInfoResponse updatePersonalInfo(TutorPersonalInfoUpdateRequest request) {
        User user = getCurrentUser();
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
        Tutor tutor = getTutor(getCurrentUser());
        return mapper.toEducation(tutor);
    }

    @Transactional
    public TutorEducationResponse updateEducation(TutorEducationUpdateRequest req) {

        Tutor tutor = getTutor(getCurrentUser());

        // --- 1. Xử lý file chứng chỉ chính (proofFile) ---
        MultipartFile proofFile = req.getProofFile();
        if (proofFile == null || proofFile.isEmpty()) {
            throw new AppException(ErrorCode.PROOF_FILE_REQUIRED);
        }

        // Kiểm tra loại file
        String contentType = proofFile.getContentType();
        if (contentType == null || !(contentType.equals("application/pdf"))) {

            throw new AppException(ErrorCode.INVALID_PROOF_FILE_TYPE);
        }
        // if (contentType == null ||
        // !(contentType.equals("application/pdf")
        // || contentType.equals("image/png")
        // || contentType.equals("image/jpeg")
        // || contentType.equals("image/jpg"))) {

        // throw new AppException(ErrorCode.INVALID_PROOF_FILE_TYPE);
        // }

        // Tạo thư mục nếu chưa có
        Path proofFolder = Paths.get(UPLOAD_ROOT, "proofs");

        try {
            Files.createDirectories(proofFolder);
        } catch (Exception e) {
            throw new AppException(ErrorCode.PROOF_FILE_UPLOAD_FAILED);
        }

        // Lấy extension
        String original = proofFile.getOriginalFilename();

        // sanitize name để tránh ký tự lỗi
        String sanitized = original.replaceAll("[^a-zA-Z0-9\\.\\-_]", "_");

        String ext = sanitized.substring(sanitized.lastIndexOf(".")); // ".pdf"

        String fileName = "proof_" + tutor.getTutorId() + "_"
                + UUID.randomUUID().toString().substring(0, 6)
                + ext;

        Path destination = proofFolder.resolve(fileName);

        // SAVE FILE
        try {
            proofFile.transferTo(destination.toFile());
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(ErrorCode.PROOF_FILE_UPLOAD_FAILED);
        }

        String fileUrl = "/uploads/tutors/proofs/" + fileName;

        // --- 2. Cập nhật các field khác ---
        tutor.setUniversity(req.getUniversity());
        tutor.setIntroduction(req.getIntroduction());
        tutor.setPricePerHour(req.getPricePerHour());
        // tutor.setProofFileUrl(fileUrl);
        // tutor.setCertificates(req.getCertificates());

        tutorRepository.save(tutor);

        return mapper.toEducation(tutor);
    }

    // ------------------------------
    // 3. SUBJECTS
    // ------------------------------

    public TutorSubjectsResponse getSubjects() {
        Tutor tutor = getTutor(getCurrentUser());
        var subjects = tutor.getSubjects()
                .stream()
                .map(s -> new TutorSubjectsResponse.SubjectItem(
                        s.getSubjectId(), s.getSubjectName()))
                .toList();

        return new TutorSubjectsResponse(subjects);
    }

    @Transactional
    public TutorSubjectsResponse updateSubjects(TutorSubjectsUpdateRequest req) {
        Tutor tutor = getTutor(getCurrentUser());

        var subjectList = subjectRepository.findAllById(req.getSubjectIds());

        // tutor.setSubjects(new HashSet<>(subjectList));
        Set<Subject> newSubjects = new HashSet<>(subjectList);
        tutor.setSubjects(newSubjects);

        tutorRepository.save(tutor);

        return getSubjects();
    }

    // 4. RATINGS SUMMARY
    public TutorRatingsSummaryResponse getRatingsSummary() {
        Tutor tutor = getTutor(getCurrentUser());

        Double avg = ratingsRepository.getAverageRating(tutor.getTutorId());
        Long count = ratingsRepository.getTotalReviews(tutor.getTutorId());

        return TutorRatingsSummaryResponse.builder()
                .averageRating(avg == null ? 0.0 : avg)
                .totalReviews(count == null ? 0 : count)
                .build();
    }

    @Transactional
    public TutorAvatarUpdateResponse updateAvatar(MultipartFile avatarFile) {

        User user = getCurrentUser();
        Tutor tutor = getTutor(user);

        if (avatarFile == null || avatarFile.isEmpty()) {
            throw new AppException(ErrorCode.AVATAR_FILE_REQUIRED);
        }

        // 1. Kiểm tra loại file (chỉ nhận JPG/PNG)
        String contentType = avatarFile.getContentType();
        if (contentType == null ||
                !(contentType.equals("image/png") ||
                        contentType.equals("image/jpeg") ||
                        contentType.equals("image/jpg"))) {
            throw new AppException(ErrorCode.AVATAR_INVALID_TYPE);
        }

        try {
            // 2. Tạo thư mục lưu file
            String uploadDir = "uploads/tutors/avatars/";
            File dir = new File(uploadDir);
            if (!dir.exists())
                dir.mkdirs();

            // 3. Tạo tên file mới (đảm bảo duy nhất)
            String extension = Objects.requireNonNull(avatarFile.getOriginalFilename())
                    .substring(avatarFile.getOriginalFilename().lastIndexOf("."));

            String fileName = "tutor_" + user.getUserId() + "_" + System.currentTimeMillis() + extension;

            File destination = new File(uploadDir + fileName);

            // 4. Ghi file vào thư mục
            avatarFile.transferTo(destination);

            // 5. Tạo đường dẫn URL lưu trong DB
            String fileUrl = "/" + uploadDir + fileName; // FE có thể load trực tiếp

            // 6. Update vào bảng users.avatar_image
            user.setAvatarImage(fileUrl);
            userRepository.save(user);

            // 7. Trả DTO response
            return TutorAvatarUpdateResponse.builder()
                    .avatarUrl(fileUrl)
                    .build();

        } catch (Exception e) {
            throw new AppException(ErrorCode.AVATAR_UPLOAD_FAILED);
        }
    }

}

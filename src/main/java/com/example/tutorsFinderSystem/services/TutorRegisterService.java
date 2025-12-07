package com.example.tutorsFinderSystem.services;

import com.example.tutorsFinderSystem.dto.request.TutorRegisterRequest;
import com.example.tutorsFinderSystem.dto.response.TutorRegisterResponse;
import com.example.tutorsFinderSystem.entities.*;
import com.example.tutorsFinderSystem.enums.Role;
import com.example.tutorsFinderSystem.enums.UserStatus;
import com.example.tutorsFinderSystem.enums.CertificateStatus;
import com.example.tutorsFinderSystem.exceptions.AppException;
import com.example.tutorsFinderSystem.exceptions.ErrorCode;
import com.example.tutorsFinderSystem.mapper.TutorMapper;
import com.example.tutorsFinderSystem.repositories.SubjectRepository;
import com.example.tutorsFinderSystem.repositories.TutorRepository;
import com.example.tutorsFinderSystem.repositories.UserRepository;
import com.example.tutorsFinderSystem.repositories.TutorCertificateRepository;
import com.example.tutorsFinderSystem.repositories.TutorCertificateFileRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TutorRegisterService {

    private final UserRepository userRepository;
    private final TutorRepository tutorRepository;
    private final SubjectRepository subjectRepository;
    private final TutorMapper tutorMapper;
    private final PasswordEncoder passwordEncoder;
    private final GoogleDriveService googleDriveService;
    private final TutorCertificateRepository tutorCertificateRepository;
    private final TutorCertificateFileRepository tutorCertificateFileRepository;

    public TutorRegisterResponse registerTutor(TutorRegisterRequest req) throws IOException {

        // Kiểm tra email
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        // Kiểm tra mật khẩu
        if (!req.getPassword().equals(req.getConfirmPassword())) {
            throw new AppException(ErrorCode.PASSWORD_NOT_MATCH);
        }

        // Kiểm tra môn học
        if (req.getSubjectIds() == null || req.getSubjectIds().isEmpty()) {
            throw new AppException(ErrorCode.SUBJECT_NOT_SELECTED);
        }

        // Upload avatar
        String avatarUrl = null;
        MultipartFile avatarFile = req.getAvatarFile();

        if (avatarFile != null && !avatarFile.isEmpty()) {
            try {
                avatarUrl = googleDriveService.upload(avatarFile, "avatars");
            } catch (IOException e) {
                throw new AppException(ErrorCode.FILE_UPLOAD_FAILED);
            }
        }

        // Tạo User
        User user = User.builder()
                .fullName(req.getFullName())
                .email(req.getEmail())
                .passwordHash(passwordEncoder.encode(req.getPassword()))
                .phoneNumber(req.getPhoneNumber())
                .avatarImage(avatarUrl)
                .role(Role.TUTOR)
                .status(UserStatus.INACTIVE)
                .build();

        userRepository.save(user);

        // Tạo Tutor
        Set<Subject> subjects = new HashSet<>(subjectRepository.findAllById(req.getSubjectIds()));

        Tutor tutor = Tutor.builder()
                .user(user)
                .gender(req.getGender())
                .address(req.getAddress())
                .university(req.getUniversity())
                .educationalLevel(req.getEducationalLevel())
                .introduction(req.getIntroduction())
                .pricePerHour(req.getPricePerHour())
                .subjects(subjects)
                .certificates(new ArrayList<>()) // tránh null
                .build();

        tutorRepository.save(tutor);

        // Upload certificates
        if (req.getCertificateNames() != null &&
                req.getCertificateFiles() != null &&
                req.getCertificateNames().size() == req.getCertificateFiles().size()) {

            for (int i = 0; i < req.getCertificateFiles().size(); i++) {

                MultipartFile file = req.getCertificateFiles().get(i);
                String certificateName = req.getCertificateNames().get(i);

                if (file == null || file.isEmpty())
                    continue;

                // 7.1 Upload file lên Google Drive
                String fileUrl;
                try {
                    fileUrl = googleDriveService.upload(file, "certificates");
                } catch (IOException e) {
                    throw new AppException(ErrorCode.FILE_UPLOAD_FAILED);
                }

                // 7.2 Tạo CERTIFICATE (thông tin logic)
                TutorCertificate certificate = TutorCertificate.builder()
                        .tutor(tutor)
                        .certificateName(certificateName)
                        .approved(false) // chưa được duyệt
                        .build();

                tutorCertificateRepository.save(certificate);

                // 7.3 Tạo file trong bảng FILE (pending)
                TutorCertificateFile certFile = TutorCertificateFile.builder()
                        .certificate(certificate)
                        .fileUrl(fileUrl)
                        .status(CertificateStatus.PENDING)
                        .isActive(false)
                        .uploadedAt(LocalDateTime.now())
                        .build();

                tutorCertificateFileRepository.save(certFile);
            }
        }

        return tutorMapper.toTutorResponse(tutor);
    }

}

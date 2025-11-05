package com.example.tutorsFinderSystem.services;

import com.example.tutorsFinderSystem.dto.request.TutorRegisterRequest;
import com.example.tutorsFinderSystem.dto.response.TutorRegisterResponse;
import com.example.tutorsFinderSystem.entities.Subject;
import com.example.tutorsFinderSystem.entities.Tutor;
import com.example.tutorsFinderSystem.entities.User;
import com.example.tutorsFinderSystem.enums.Role;
import com.example.tutorsFinderSystem.enums.UserStatus;
import com.example.tutorsFinderSystem.exceptions.AppException;
import com.example.tutorsFinderSystem.exceptions.ErrorCode;
import com.example.tutorsFinderSystem.mapper.TutorMapper;
import com.example.tutorsFinderSystem.repositories.SubjectRepository;
import com.example.tutorsFinderSystem.repositories.TutorRepository;
import com.example.tutorsFinderSystem.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TutorRegisterService {

    private final UserRepository userRepository;
    private final TutorRepository tutorRepository;
    private final SubjectRepository subjectRepository;
    private final TutorMapper tutorMapper;
    private final PasswordEncoder passwordEncoder;

    // Đường dẫn upload cố định, nằm cùng cấp project
    private static final String UPLOAD_ROOT = System.getProperty("user.dir") + "/uploads/tutors/";

    public TutorRegisterResponse registerTutor(TutorRegisterRequest request) throws IOException {
        // Kiểm tra email trùng
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        // Kiểm tra confirm password
        if (!Objects.equals(request.getPassword(), request.getConfirmPassword())) {
            throw new AppException(ErrorCode.PASSWORD_NOT_MATCH);
        }

        // Kiểm tra subject
        if (request.getSubjectIds() == null || request.getSubjectIds().isEmpty()) {
            throw new AppException(ErrorCode.SUBJECT_NOT_SELECTED);
        }

        // Lưu file
        String avatarUrl = saveFile(request.getAvatarFile(), "avatars");
        String proofUrl = saveFile(request.getProofFile(), "proofs");

        // Tạo user
        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .avatarImage(avatarUrl)
                .role(Role.TUTOR)
                .status(UserStatus.ACTIVE)
                .build();
        userRepository.save(user);

        // Lấy danh sách môn học
        Set<Subject> subjects = new HashSet<>(subjectRepository.findAllById(request.getSubjectIds()));

        // Tạo tutor entity
        Tutor tutor = Tutor.builder()
                .user(user)
                .gender(request.getGender())
                .address(request.getAddress())
                .university(request.getUniversity())
                .certificates(request.getCertificates())
                .proofFileUrl(proofUrl)
                .educationalLevel(request.getEducationalLevel())
                .introduction(request.getIntroduction())
                .pricePerHour(request.getPricePerHour())
                .subjects(subjects)
                .build();
        tutorRepository.save(tutor);

        // Trả về response
        return tutorMapper.toTutorResponse(tutor);
    }

    private String saveFile(MultipartFile file, String folder) throws IOException {
        if (file == null || file.isEmpty())
            return null;

        // Tạo thư mục cha nếu chưa có
        Path folderPath = Paths.get(UPLOAD_ROOT + folder);
        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
        }

        // Tạo tên file mới
        String fileName = UUID.randomUUID().toString().substring(0, 5) + "_" + file.getOriginalFilename();
        Path filePath = folderPath.resolve(fileName);
        if (filePath == null) {
            throw new AppException(ErrorCode.URL_REQUIRE);
        }

        // Ghi file
        try {
            file.transferTo(filePath.toFile());
            System.out.println("File đã lưu: " + filePath.toAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            throw new AppException(ErrorCode.FILE_SAVE_ERROR);
        }

        // Trả về URL tương đối
        return "/uploads/tutors/" + folder + "/" + fileName;
    }
}

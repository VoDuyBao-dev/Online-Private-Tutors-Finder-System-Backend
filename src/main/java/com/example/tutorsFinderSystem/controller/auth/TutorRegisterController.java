package com.example.tutorsFinderSystem.controller.auth;

import com.example.tutorsFinderSystem.dto.request.TutorRegisterRequest;
import com.example.tutorsFinderSystem.dto.response.TutorRegisterResponse;
import com.example.tutorsFinderSystem.services.TutorRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("auth/tutors")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TutorRegisterController {

    private final TutorRegisterService tutorRegisterService;

    @PostMapping(value = "/register", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> registerTutor(
            @RequestPart("data") TutorRegisterRequest request,
            @RequestPart(value = "avatarFile", required = false) MultipartFile avatarFile,
            @RequestPart(value = "proofFile", required = false) MultipartFile proofFile) {
        try {
            System.out.println("Request received:");
            System.out.println("   Avatar file: " + (avatarFile != null ? avatarFile.getOriginalFilename() : "null"));
            System.out.println("   Proof file: " + (proofFile != null ? proofFile.getOriginalFilename() : "null"));
            request.setAvatarFile(avatarFile);
            request.setProofFile(proofFile);

            TutorRegisterResponse response = tutorRegisterService.registerTutor(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

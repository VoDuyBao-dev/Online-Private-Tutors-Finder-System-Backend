package com.example.tutorsFinderSystem.controller;

import com.example.tutorsFinderSystem.services.GoogleDriveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/drive")
@RequiredArgsConstructor
public class FileController {

    private final GoogleDriveService driveService;

    // Upload test
    @PostMapping("/upload")
    public ResponseEntity<?> upload(
            @RequestPart("file") MultipartFile file,
            @RequestParam("folder") String folderKey
    ) {
        try {
            String url = driveService.upload(file, folderKey);
            return ResponseEntity.ok(url);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    // Download test
    @GetMapping("/download/{fileId}")
    public ResponseEntity<?> download(@PathVariable String fileId) {
        try {
            byte[] content = driveService.download(fileId);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(content);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Delete test
    @DeleteMapping("/delete/{fileId}")
    public ResponseEntity<?> delete(@PathVariable String fileId) {
        try {
            driveService.delete(fileId);
            return ResponseEntity.ok("Deleted!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

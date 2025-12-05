package com.example.tutorsFinderSystem.controller;

import com.example.tutorsFinderSystem.services.GoogleDriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private GoogleDriveService googleDriveService;

    // API Upload file
    // POST /api/files/upload
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // Folder ID là ID của thư mục trên Drive bạn muốn lưu (lấy từ URL thư mục trên Drive)
            // Có thể để null nếu muốn lưu ở Root
            String folderId = "PASTE_YOUR_FOLDER_ID_HERE_OR_NULL"; 
            String fileId = googleDriveService.uploadFile(file, folderId);
            return ResponseEntity.ok(fileId); // Trả về ID để lưu vào Database
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Upload failed: " + e.getMessage());
        }
    }

    // API Xem file (Proxy hình ảnh)
    // GET /api/files/view/{fileId}
    @GetMapping("/view/{fileId}")
    public ResponseEntity<byte[]> viewFile(@PathVariable String fileId) {
        try {
            byte[] fileContent = googleDriveService.getFileContent(fileId);
            
            // Bạn có thể cần xác định MediaType chính xác (image/png, image/jpeg, pdf, v.v.)
            // Ở đây mình để mặc định là IMAGE_JPEG, nhưng tốt nhất là lưu loại file trong DB
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) 
                    .body(fileContent);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
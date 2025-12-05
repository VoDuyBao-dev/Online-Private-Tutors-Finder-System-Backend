package com.example.tutorsFinderSystem.services;

import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
// import java.io.InputStream;
import java.util.Collections;

@Service
public class GoogleDriveService {

    @Autowired
    private Drive googleDrive;

    // Upload file và trả về File ID của Google Drive
    public String uploadFile(MultipartFile file, String folderId) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("File is empty");
        }

        File fileMetadata = new File();
        fileMetadata.setName(file.getOriginalFilename());
        
        // Nếu muốn lưu vào một thư mục cụ thể trên Drive, hãy truyền folderId vào
        // Nếu không, bỏ qua dòng setParents để lưu ở thư mục gốc (Root)
        if (folderId != null && !folderId.isEmpty()) {
            fileMetadata.setParents(Collections.singletonList(folderId));
        }

        InputStreamContent mediaContent = new InputStreamContent(
                file.getContentType(),
                file.getInputStream()
        );

        File uploadedFile = googleDrive.files().create(fileMetadata, mediaContent)
                .setFields("id") // Chỉ cần trả về ID
                .execute();

        return uploadedFile.getId();
    }

    // Lấy nội dung file (byte array) để hiển thị ra UI
    public byte[] getFileContent(String fileId) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        googleDrive.files().get(fileId).executeMediaAndDownloadTo(outputStream);
        return outputStream.toByteArray();
    }
}
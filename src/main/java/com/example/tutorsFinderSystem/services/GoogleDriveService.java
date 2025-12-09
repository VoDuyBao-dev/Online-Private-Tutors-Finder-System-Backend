package com.example.tutorsFinderSystem.services;

import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GoogleDriveService {

    private final Drive drive;

    @Value("${drive.folder.avatars}")
    private String avatarsFolder;

    @Value("${drive.folder.certificates}")
    private String certificatesFolder;

    @Value("${drive.folder.ebooks}")
    private String ebooksFolder;

    @Value("${drive.folder.stickers}")
    private String stickersFolder;

    private Map<String, String> folderMap() {
        return Map.of(
                "avatars", avatarsFolder,
                "certificates", certificatesFolder,
                "ebooks", ebooksFolder,
                "stickers", stickersFolder);
    }

    // ---------------------------
    // 1) UPLOAD FILE
    // ---------------------------
    public String upload(MultipartFile file, String folderKey) throws IOException {
        try {
            String folderId = folderMap().get(folderKey);
            if (folderId == null) {
                throw new IOException("Invalid folder key: " + folderKey);
            }

            String contentType = file.getContentType();
            if (contentType == null || contentType.isBlank()) {
                contentType = "application/octet-stream";
            }

            File fileMeta = new File();
            fileMeta.setName(file.getOriginalFilename());
            fileMeta.setParents(Collections.singletonList(folderId));

            InputStreamContent mediaContent = new InputStreamContent(contentType, file.getInputStream());
            mediaContent.setLength(file.getSize()); // quan trọng

            File uploaded = drive.files()
                    .create(fileMeta, mediaContent)
                    .setFields("id")
                    .setSupportsAllDrives(true)
                    .execute();

            return "https://drive.google.com/uc?id=" + uploaded.getId();

        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Failed to upload file to Google Drive: " + e.getMessage(), e);
        }
    }

    // ---------------------------
    // 2) DOWNLOAD FILE (tải về)
    // ---------------------------
    public byte[] download(String fileId) throws IOException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            drive.files()
                    .get(fileId)
                    .executeMediaAndDownloadTo(outputStream);

            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new IOException("Failed to download file from Google Drive: " + e.getMessage(), e);
        }
    }

    // ---------------------------
    // 3) DELETE FILE (xóa)
    // ---------------------------
    public void delete(String fileId) throws IOException {
        try {
            drive.files()
                    .delete(fileId)
                    .setSupportsAllDrives(true) // CỰC KỲ QUAN TRỌNG
                    .execute();
        } catch (Exception e) {
            throw new IOException("Failed to delete file from Google Drive: " + e.getMessage(), e);
        }
    }

    // (Optional) lấy info file nếu cần
    public File getFileInfo(String fileId) throws IOException {
        try {
            return drive.files()
                    .get(fileId)
                    .setFields("id, name, size, mimeType, webViewLink")
                    .execute();
        } catch (Exception e) {
            throw new IOException("Failed to get file info from Google Drive: " + e.getMessage(), e);
        }
    }

    public byte[] getFileBytes(String fileId) {
        try {
            // Gọi Google Drive API để lấy nội dung file
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            drive.files()
                    .get(fileId)
                    .executeMediaAndDownloadTo(outputStream);

            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Không đọc được file từ Google Drive: " + fileId, e);
        }
    }

}

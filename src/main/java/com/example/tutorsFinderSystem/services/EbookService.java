package com.example.tutorsFinderSystem.services;

import com.example.tutorsFinderSystem.dto.PageResponse;
import com.example.tutorsFinderSystem.dto.request.EbookCreateRequest;
import com.example.tutorsFinderSystem.dto.request.EbookUpdateRequest;
import com.example.tutorsFinderSystem.dto.response.EbookResponse;
import com.example.tutorsFinderSystem.entities.Ebook;
import com.example.tutorsFinderSystem.entities.User;
import com.example.tutorsFinderSystem.enums.EbookType;
import com.example.tutorsFinderSystem.exceptions.AppException;
import com.example.tutorsFinderSystem.exceptions.ErrorCode;
import com.example.tutorsFinderSystem.mapper.EbookMapper;
import com.example.tutorsFinderSystem.repositories.EbookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class EbookService {

    private final EbookRepository ebookRepository;
    private final EbookMapper ebookMapper;
    private final UserService userService;
    private final GoogleDriveService googleDriveService;

    // ========================================================
    // CREATE EBOOK (JSON + FILE → Google Drive)
    // ========================================================
    public EbookResponse createEbook(EbookCreateRequest request, MultipartFile ebookFile) {

        if (ebookFile == null || ebookFile.isEmpty()) {
            throw new AppException(ErrorCode.EBOOK_FILE_REQUIRED);
        }

        User uploader = userService.getCurrentUser();

        Ebook ebook = ebookMapper.toEbook(request);
        ebook.setUploadedBy(uploader);

        try {
            String driveUrl = googleDriveService.upload(ebookFile, "ebooks");
            ebook.setFilePath(driveUrl);
        } catch (IOException e) {
            throw new AppException(ErrorCode.FILE_UPLOAD_FAILED);
        }

        Ebook saved = ebookRepository.save(ebook);
        return ebookMapper.toEbookResponse(saved);
    }

    // ========================================================
    // UPDATE EBOOK (metadata + optional new file)
    // ========================================================
    public EbookResponse updateEbook(Long ebookId, EbookUpdateRequest request, MultipartFile ebookFile) {

        Ebook ebook = ebookRepository.findById(ebookId)
                .orElseThrow(() -> new AppException(ErrorCode.EBOOK_NOT_FOUND));

        // Update metadata trước
        ebookMapper.updateEbookFromRequest(request, ebook);

        // Nếu có upload file mới → upload Drive và xoá file cũ
        if (ebookFile != null && !ebookFile.isEmpty()) {

            // Lấy id file cũ
            String oldUrl = ebook.getFilePath();
            String oldId = extractDriveId(oldUrl);

            try {
                String newUrl = googleDriveService.upload(ebookFile, "ebooks");
                ebook.setFilePath(newUrl);

                // Xoá file cũ
                if (oldId != null) {
                    googleDriveService.delete(oldId);
                }

            } catch (IOException e) {
                throw new AppException(ErrorCode.FILE_UPLOAD_FAILED);
            }
        }

        Ebook updated = ebookRepository.save(ebook);
        return ebookMapper.toEbookResponse(updated);
    }

    // ========================================================
    // DELETE EBOOK (xoá cả file Drive)
    // ========================================================
    public void deleteEbook(Long ebookId) {

        Ebook ebook = ebookRepository.findById(ebookId)
                .orElseThrow(() -> new AppException(ErrorCode.EBOOK_NOT_FOUND));

        String fileId = extractDriveId(ebook.getFilePath());

        if (fileId != null) {
            try {
                googleDriveService.delete(fileId);
            } catch (IOException ignored) {
            }
        }

        ebookRepository.delete(ebook);
    }

    // ========================================================
    // GET EBOOK
    // ========================================================
    @Transactional(readOnly = true)
    public EbookResponse getEbookById(Long ebookId) {

        Ebook ebook = ebookRepository.findById(ebookId)
                .orElseThrow(() -> new AppException(ErrorCode.EBOOK_NOT_FOUND));

        return ebookMapper.toEbookResponse(ebook);
    }

    // ========================================================
    // LIST EBOOK (filter + paging)
    // ========================================================
    @Transactional(readOnly = true)
    public PageResponse<EbookResponse> getAllEbooks(EbookType type, String keyword, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Ebook> ebookPage;

        boolean hasType = type != null;
        boolean hasKeyword = keyword != null && !keyword.isBlank();

        if (hasType && hasKeyword) {
            ebookPage = ebookRepository.findByTypeAndTitleContainingIgnoreCase(type, keyword.trim(), pageable);
        } else if (hasType) {
            ebookPage = ebookRepository.findByType(type, pageable);
        } else if (hasKeyword) {
            ebookPage = ebookRepository.findByTitleContainingIgnoreCase(keyword.trim(), pageable);
        } else {
            ebookPage = ebookRepository.findAll(pageable);
        }

        return PageResponse.<EbookResponse>builder()
                .items(ebookMapper.toEbookResponses(ebookPage.getContent()))
                .page(page)
                .size(size)
                .totalItems(ebookPage.getTotalElements())
                .totalPages(ebookPage.getTotalPages())
                .build();
    }

    // ========================================================
    // HELPER – Tách fileId từ Google Drive URL
    // ========================================================
    private String extractDriveId(String url) {
        if (url == null)
            return null;
        if (!url.contains("id="))
            return null;

        return url.substring(url.indexOf("id=") + 3);
    }

    @Transactional(readOnly = true)
    public PageResponse<EbookResponse> searchEbooks(
            Long subjectId,
            EbookType type,
            LocalDate createdDate,
            int page,
            int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<Ebook> ebookPage = ebookRepository.searchEbooks(
                subjectId,
                type,
                createdDate,
                pageable);

        return PageResponse.<EbookResponse>builder()
                .items(ebookMapper.toEbookResponses(ebookPage.getContent()))
                .page(page)
                .size(size)
                .totalItems(ebookPage.getTotalElements())
                .totalPages(ebookPage.getTotalPages())
                .build();
    }

}

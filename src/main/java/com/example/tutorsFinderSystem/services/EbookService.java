package com.example.tutorsFinderSystem.services;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EbookService {

    private final EbookRepository ebookRepository;
    private final EbookMapper ebookMapper;
    private final UserService userService; // dùng để lấy User hiện tại

    public EbookResponse createEbook(EbookCreateRequest request) {

        User currentUser = userService.getCurrentUser();

        Ebook ebook = ebookMapper.toEbook(request);
        ebook.setUploadedBy(currentUser);

        Ebook saved = ebookRepository.save(ebook);

        return ebookMapper.toEbookResponse(saved);
    }

    public EbookResponse updateEbook(Long ebookId, EbookUpdateRequest request) {

        Ebook ebook = ebookRepository.findById(ebookId)
                .orElseThrow(() -> new AppException(ErrorCode.EBOOK_NOT_FOUND));

        // Nếu cần check quyền admin thì check ở đây
        // User current = userService.getCurrentUser();

        ebookMapper.updateEbookFromRequest(request, ebook);

        Ebook updated = ebookRepository.save(ebook);

        return ebookMapper.toEbookResponse(updated);
    }

    public void deleteEbook(Long ebookId) {

        Ebook ebook = ebookRepository.findById(ebookId)
                .orElseThrow(() -> new AppException(ErrorCode.EBOOK_NOT_FOUND));

        ebookRepository.delete(ebook);
    }

    @Transactional(readOnly = true)
    public EbookResponse getEbookById(Long ebookId) {

        Ebook ebook = ebookRepository.findById(ebookId)
                .orElseThrow(() -> new AppException(ErrorCode.EBOOK_NOT_FOUND));

        return ebookMapper.toEbookResponse(ebook);
    }

    
    @Transactional(readOnly = true)
    public List<EbookResponse> getAllEbooks(EbookType type, String keyword) {

        List<Ebook> ebooks;

        boolean hasType = type != null;
        boolean hasKeyword = keyword != null && !keyword.isBlank();

        if (hasType && hasKeyword) {
            ebooks = ebookRepository.findByTypeAndTitleContainingIgnoreCase(type, keyword.trim());
        } else if (hasType) {
            ebooks = ebookRepository.findByType(type);
        } else if (hasKeyword) {
            ebooks = ebookRepository.findByTitleContainingIgnoreCase(keyword.trim());
        } else {
            ebooks = ebookRepository.findAll();
        }

        return ebookMapper.toEbookResponses(ebooks);
    }
}

package com.example.tutorsFinderSystem.repositories;

import com.example.tutorsFinderSystem.entities.Ebook;
import com.example.tutorsFinderSystem.enums.EbookType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

// import java.util.List;

public interface EbookRepository extends JpaRepository<Ebook, Long> {

    // List<Ebook> findByType(EbookType type);

    // List<Ebook> findByTitleContainingIgnoreCase(String keyword);

    // List<Ebook> findByTypeAndTitleContainingIgnoreCase(EbookType type, String keyword);

    Page<Ebook> findByType(EbookType type, Pageable pageable);

    Page<Ebook> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);

    Page<Ebook> findByTypeAndTitleContainingIgnoreCase(EbookType type, String keyword, Pageable pageable);

}

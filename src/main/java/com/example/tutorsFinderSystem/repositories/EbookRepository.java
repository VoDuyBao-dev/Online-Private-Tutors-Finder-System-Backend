package com.example.tutorsFinderSystem.repositories;

import com.example.tutorsFinderSystem.entities.Ebook;
import com.example.tutorsFinderSystem.enums.EbookType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EbookRepository extends JpaRepository<Ebook, Long> {

    List<Ebook> findByType(EbookType type);

    List<Ebook> findByTitleContainingIgnoreCase(String keyword);

    List<Ebook> findByTypeAndTitleContainingIgnoreCase(EbookType type, String keyword);
}

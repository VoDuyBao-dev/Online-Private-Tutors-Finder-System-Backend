package com.example.tutorsFinderSystem.services;

import com.example.tutorsFinderSystem.dto.common.RelatedClassDTO;
import com.example.tutorsFinderSystem.entities.ClassEntity;
import com.example.tutorsFinderSystem.entities.Tutor;
import com.example.tutorsFinderSystem.entities.User;
import com.example.tutorsFinderSystem.mapper.ClassesMapper;
import com.example.tutorsFinderSystem.repositories.ClassRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClassEntityService {
    ClassRepository classRepository;
    ClassesMapper classesMapper;

//    Lấy danh sách lớp học liên quan dựa trên môn học và gia sư
    @Transactional(readOnly = true)
    public List<RelatedClassDTO> getRelatedClasses(Long classId, Long subjectId, Long tutorId, int limit) {
        Pageable pageable = PageRequest.of(0, limit);

        return classRepository.findRelatedClasses(subjectId, tutorId, classId, pageable)
                .stream()
                .map(classesMapper::toRelatedClassDTO)
                .toList();
    }


}

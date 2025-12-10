package com.example.tutorsFinderSystem.mapper;

import com.example.tutorsFinderSystem.dto.common.SubjectDTO;
import com.example.tutorsFinderSystem.entities.Subject;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface SubjectMapper {
    SubjectDTO toSubjectDTO(Subject subject);
}

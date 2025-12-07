package com.example.tutorsFinderSystem.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.tutorsFinderSystem.dto.response.TutorPersonalInfoResponse;
import com.example.tutorsFinderSystem.dto.response.TutorEducationResponse;
import com.example.tutorsFinderSystem.entities.Tutor;
import com.example.tutorsFinderSystem.entities.TutorCertificate;
import com.example.tutorsFinderSystem.entities.User;

@Mapper(componentModel = "spring")
public interface TutorProfileMapper {

    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "user.fullName", target = "fullName")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.phoneNumber", target = "phoneNumber")
    @Mapping(source = "user.avatarImage", target = "avatarUrl")
    TutorPersonalInfoResponse toPersonalInfo(User user, Tutor tutor);

    TutorEducationResponse toEducation(Tutor tutor);

    default List<String> mapCertificates(List<TutorCertificate> certificates) {
        if (certificates == null || certificates.isEmpty())
            return List.of();
        return certificates.stream()
                .map(TutorCertificate::getCertificateName)
                .collect(Collectors.toList());
    }
}

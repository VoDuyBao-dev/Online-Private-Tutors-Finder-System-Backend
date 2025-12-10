package com.example.tutorsFinderSystem.mapper;

import com.example.tutorsFinderSystem.dto.common.CertificateDTO;
import com.example.tutorsFinderSystem.entities.TutorCertificateFile;
import com.example.tutorsFinderSystem.entities.TutorCertificate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CertificateMapper {

    @Mapping(target = "certificateId", source = "certificate.certificateId")
    @Mapping(target = "certificateName", source = "certificate.certificateName")
    @Mapping(target = "fileUrl", source = "file.fileUrl")
    @Mapping(target = "uploadedAt", source = "file.uploadedAt")
    CertificateDTO toCertificateDTO(TutorCertificate certificate, TutorCertificateFile file);
}

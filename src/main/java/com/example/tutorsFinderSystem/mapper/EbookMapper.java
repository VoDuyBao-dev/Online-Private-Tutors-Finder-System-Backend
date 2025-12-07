package com.example.tutorsFinderSystem.mapper;

import com.example.tutorsFinderSystem.dto.request.EbookCreateRequest;
import com.example.tutorsFinderSystem.dto.request.EbookUpdateRequest;
import com.example.tutorsFinderSystem.dto.response.EbookResponse;
import com.example.tutorsFinderSystem.entities.Ebook;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EbookMapper {

    @Mapping(source = "uploadedBy.userId", target = "uploadedById")
    @Mapping(source = "uploadedBy.fullName", target = "uploadedByName")
    EbookResponse toEbookResponse(Ebook ebook);

    List<EbookResponse> toEbookResponses(List<Ebook> ebooks);

    // Khi tạo ebook → filePath sẽ được set trong service
    Ebook toEbook(EbookCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEbookFromRequest(EbookUpdateRequest request, @MappingTarget Ebook ebook);
}

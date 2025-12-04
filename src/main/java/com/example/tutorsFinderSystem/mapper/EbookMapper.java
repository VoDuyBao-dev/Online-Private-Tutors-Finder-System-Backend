package com.example.tutorsFinderSystem.mapper;

import com.example.tutorsFinderSystem.dto.request.EbookCreateRequest;
import com.example.tutorsFinderSystem.dto.request.EbookUpdateRequest;
import com.example.tutorsFinderSystem.dto.response.EbookResponse;
import com.example.tutorsFinderSystem.entities.Ebook;
import com.example.tutorsFinderSystem.entities.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EbookMapper {

    // Map từ entity -> response
    @Mapping(source = "uploadedBy.userId", target = "uploadedById")
    @Mapping(source = "uploadedBy.fullName", target = "uploadedByName")
    EbookResponse toEbookResponse(Ebook ebook);

    List<EbookResponse> toEbookResponses(List<Ebook> ebooks);

    // Map từ request -> entity (create)
    // @Mapping(target = "ebookId", ignore = true)
    // @Mapping(target = "uploadedBy", ignore = true)   // set trong service
    // @Mapping(target = "createdAt", ignore = true)    // dùng default trong entity
    Ebook toEbook(EbookCreateRequest request);

    // Update entity từ request (update), bỏ qua field null
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    // @Mapping(target = "ebookId", ignore = true)
    // @Mapping(target = "uploadedBy", ignore = true)
    // @Mapping(target = "createdAt", ignore = true)
    void updateEbookFromRequest(EbookUpdateRequest request, @MappingTarget Ebook ebook);

    // Nếu cần map User -> thông tin upload khác, có thể khai báo thêm method ở đây.
}

package com.example.tutorsFinderSystem.mapper;

import com.example.tutorsFinderSystem.dto.request.EbookCreateRequest;
import com.example.tutorsFinderSystem.dto.request.EbookUpdateRequest;
import com.example.tutorsFinderSystem.dto.response.EbookResponse;
import com.example.tutorsFinderSystem.entities.Ebook;

import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EbookMapper {

    // MAP ENTITY → DTO
    // ============================
    @Mapping(source = "uploadedBy.userId", target = "uploadedById")
    @Mapping(source = "uploadedBy.fullName", target = "uploadedByName")
    @Mapping(target = "filePath", expression = "java(buildFilePath(ebook))")
    EbookResponse toEbookResponse(Ebook ebook);

    List<EbookResponse> toEbookResponses(List<Ebook> ebookList);


    // ============================
    // CREATE → ENTITY
    // ============================
    @Mapping(target = "filePath", ignore = true)
    Ebook toEbook(EbookCreateRequest request);


    // ============================
    // UPDATE → ENTITY
    // ============================
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "filePath", ignore = true)
    void updateEbookFromRequest(EbookUpdateRequest request, @MappingTarget Ebook ebook);


    default String buildFilePath(Ebook ebook) {
        String url = ebook.getFilePath();
        if (url == null) return null;

        // ?id=<id>
        if (url.contains("id=")) {
            String id = url.substring(url.indexOf("id=") + 3);
            return "https://drive.google.com/file/d/" + id + "/preview";
        }

        // file/d/<id>/view
        if (url.contains("/file/d/")) {
            String id = url.split("/file/d/")[1].split("/")[0];
            return "https://drive.google.com/file/d/" + id + "/preview";
        }

        // open?id=<id>
        if (url.contains("open?id=")) {
            String id = url.substring(url.indexOf("open?id=") + 8);
            return "https://drive.google.com/file/d/" + id + "/preview";
        }

        return url;
    }
}

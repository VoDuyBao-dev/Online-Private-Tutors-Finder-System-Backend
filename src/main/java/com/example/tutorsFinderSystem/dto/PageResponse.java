package com.example.tutorsFinderSystem.dto;
import java.util.List;

import com.example.tutorsFinderSystem.dto.response.PagedResponse;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResponse<T> {
    private List<T> items;   // danh sách bản ghi mỗi trang
    private int page;        // trang hiện tại
    private int size;        // số lượng mỗi trang
    private long totalItems; // tổng số bản ghi
    private int totalPages;  // tổng số trang
    
    public static <T> PagedResponse<T> of(
            List<T> content, int page, int size, long totalElements, int totalPages) {
        return new PagedResponse<>(content, page, size, totalElements, totalPages);
    }
}

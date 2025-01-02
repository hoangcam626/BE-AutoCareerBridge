package com.backend.autocarrerbridge.dto.response.paging;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagingResponse<T> {
    private int totalPages; // Tổng số trang
    private long totalElements; // Tổng số bản ghi
    private int pageSize; // Kích thước mỗi trang
    private int currentPage;
    private List<T> content; // Dữ liệu của trang hiện tại trang hiện tại

    public PagingResponse(Page<T> page) {
        this.content = page.getContent();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.pageSize = page.getSize();
        this.currentPage = page.getNumber() + 1;
    }
}

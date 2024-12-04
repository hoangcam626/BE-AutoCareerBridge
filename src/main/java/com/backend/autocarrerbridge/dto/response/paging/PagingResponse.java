package com.backend.autocarrerbridge.dto.response.paging;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagingResponse<T> {
    private int totalPages;        // Tổng số trang
    private long totalElements;    // Tổng số bản ghi
    private int pageSize;          // Kích thước mỗi trang
    private int currentPage;
    private List<T> content;       // Dữ liệu của trang hiện tại trang hiện tại

    public PagingResponse(Page<T> page) {
        this.content = page.getContent();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.pageSize = page.getSize();
        this.currentPage = page.getNumber() + 1;
    }
}

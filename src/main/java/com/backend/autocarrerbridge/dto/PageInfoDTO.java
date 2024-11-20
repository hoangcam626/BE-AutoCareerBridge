package com.backend.autocarrerbridge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor(staticName = "of")
public class PageInfoDTO {
    private Integer currentPage;
    private Integer pageSize;

    public Integer getCurrentPage() {
        return Objects.isNull(currentPage) ? 0 : currentPage;
    }

    public Integer getPageSize() {
        return Objects.isNull(pageSize) ? 10 : pageSize;
    }
}

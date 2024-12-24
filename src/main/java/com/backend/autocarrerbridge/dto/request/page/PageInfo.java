package com.backend.autocarrerbridge.dto.request.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Data
@Builder
public class PageInfo {
    private Integer pageNo;
    private Integer pageSize;
    private String keyword;
}

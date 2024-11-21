package com.backend.autocarrerbridge.dto.industry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndustryPaging {
    private Long totalRecords;
    private List<IndustrySdo> data;
}

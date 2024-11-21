package com.backend.autocarrerbridge.dto.industry;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndustryPaging {
    private Long totalRecords;
    private List<IndustrySdo> data;
}

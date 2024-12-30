package com.backend.autocarrerbridge.dto.response.industry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndustrySalaryDTO {
    private String industryName;
    private String avgSalary;

    public IndustrySalaryDTO(String industryName, Double avgSalary) {
        this.industryName = industryName;
        // Định dạng số thành chuỗi rõ ràng
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        this.avgSalary = decimalFormat.format(avgSalary);
    }
}

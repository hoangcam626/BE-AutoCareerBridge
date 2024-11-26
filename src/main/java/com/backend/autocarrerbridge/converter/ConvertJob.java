package com.backend.autocarrerbridge.converter;


import com.backend.autocarrerbridge.dto.response.business.BusinessResponse;
import com.backend.autocarrerbridge.dto.response.employee.EmployeeResponse;
import com.backend.autocarrerbridge.dto.response.industry.IndustryResponse;
import com.backend.autocarrerbridge.dto.response.job.JobDetailResponse;
import com.backend.autocarrerbridge.entity.Business;
import com.backend.autocarrerbridge.entity.Employee;
import com.backend.autocarrerbridge.entity.Industry;
import com.backend.autocarrerbridge.entity.Job;
import org.springframework.stereotype.Component;

@Component

public class ConvertJob {

    /** Chuyển đối các đối tượng (Job, Industry, Business, Employee) thành JobDetailResponse*/
    public JobDetailResponse toJobDetailResponse(Job job, Industry industry, Business business, Employee employee) {
        return new JobDetailResponse(
                job,
                new IndustryResponse(industry),
                new BusinessResponse(business),
                new EmployeeResponse(employee)
        );
    }
}

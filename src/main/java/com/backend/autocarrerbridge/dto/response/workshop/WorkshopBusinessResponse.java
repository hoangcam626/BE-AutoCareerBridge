package com.backend.autocarrerbridge.dto.response.workshop;

import java.util.List;

import com.backend.autocarrerbridge.dto.response.business.BusinessColabResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkshopBusinessResponse {
    private WorkshopResponse workshop;
    private List<BusinessColabResponse> businessList;
}

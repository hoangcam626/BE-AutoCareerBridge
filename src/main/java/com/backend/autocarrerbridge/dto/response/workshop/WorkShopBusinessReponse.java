package com.backend.autocarrerbridge.dto.response.workshop;


import com.backend.autocarrerbridge.dto.response.business.BusinessResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkShopBusinessReponse {
    private WorkShopResponse workshop;
    private List<BusinessResponse> businessList;
}

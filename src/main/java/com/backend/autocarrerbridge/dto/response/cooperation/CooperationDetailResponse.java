package com.backend.autocarrerbridge.dto.response.cooperation;

import com.backend.autocarrerbridge.dto.response.business.BusinessResponse;
import com.backend.autocarrerbridge.util.enums.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CooperationDetailResponse {
    Integer id;

    State statusConnected;

    BusinessResponse business;
}

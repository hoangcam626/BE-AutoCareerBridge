package com.backend.autocarrerbridge.dto.response.workshop;

import com.backend.autocarrerbridge.util.enums.State;
import lombok.Data;

@Data
public class StateWorkShopBusinessResponse {
    private Integer workshopId;
    private Integer businessId;
    private State statusConnected;
}

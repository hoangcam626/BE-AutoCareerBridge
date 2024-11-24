package com.backend.autocarrerbridge.dto.request.workshop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkShopBusinessRequest {
    private Integer businessID;
    private Integer workshopID;
}

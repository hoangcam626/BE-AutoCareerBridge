package com.backend.autocarrerbridge.dto.request.workshop;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import static com.backend.autocarrerbridge.util.Constant.NO_CONTENT_MESSAGE;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkShopBusinessRequest {
    @NotBlank(message = NO_CONTENT_MESSAGE)
    private Integer businessID;
    @NotBlank(message = NO_CONTENT_MESSAGE)
    private Integer workshopID;
}

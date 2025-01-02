package com.backend.autocarrerbridge.dto.request.business;

import static com.backend.autocarrerbridge.util.Constant.NO_CONTENT_MESSAGE;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class BusinessApprovedRequest {
    @NotNull(message = NO_CONTENT_MESSAGE)
    private Integer id;
}

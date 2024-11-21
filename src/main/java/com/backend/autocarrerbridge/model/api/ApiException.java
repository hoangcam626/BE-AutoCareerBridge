package com.backend.autocarrerbridge.model.api;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class ApiException {
    private int code;
    private String message;
}

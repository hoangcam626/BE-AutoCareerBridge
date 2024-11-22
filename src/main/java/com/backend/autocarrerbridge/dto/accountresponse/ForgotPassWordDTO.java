package com.backend.autocarrerbridge.dto.accountresponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ForgotPassWordDTO {
    private String email;
    private String forgotCode;
}

package com.backend.autocarrerbridge.dto.AccountRespone;

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

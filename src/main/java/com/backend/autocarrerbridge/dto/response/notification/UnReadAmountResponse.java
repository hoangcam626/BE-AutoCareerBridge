package com.backend.autocarrerbridge.dto.response.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class UnReadAmountResponse {
    private Long amount;
}

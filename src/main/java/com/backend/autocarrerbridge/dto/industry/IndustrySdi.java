package com.backend.autocarrerbridge.dto.industry;

import com.backend.autocarrerbridge.util.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndustrySdi {
    private String name;
    private String code;
    private Status status;
}

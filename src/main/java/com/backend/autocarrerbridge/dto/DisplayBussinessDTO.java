package com.backend.autocarrerbridge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisplayBussinessDTO {
    private String username;
    private String name;
    private String taxCode;
    private String email;
}

package com.backend.autocarrerbridge.dto.AccountRespone;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DisplayBusinessDTO {
     String username;
     String name;
     String taxCode;
     String email;
}

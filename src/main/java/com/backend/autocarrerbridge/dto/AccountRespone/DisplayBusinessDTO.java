package com.backend.autocarrerbridge.dto.AccountRespone;

import com.backend.autocarrerbridge.util.enums.State;
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
     String name;
     String taxCode;
     String email;
     RoleDTO role;
     State state;
}

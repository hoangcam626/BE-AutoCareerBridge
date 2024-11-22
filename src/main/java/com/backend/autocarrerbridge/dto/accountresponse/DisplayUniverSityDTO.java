package com.backend.autocarrerbridge.dto.accountresponse;

import com.backend.autocarrerbridge.util.enums.State;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DisplayUniverSityDTO {
     String username;
     String name;
     String email;
     String phone;
     RoleDTO role;
     State state;

}

package com.backend.autocarrerbridge.dto.AccountRespone;

import com.backend.autocarrerbridge.util.enums.Status;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;


import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAccountDTO {
     Integer id;
     String username;

     String password;
     Status status;

      LocalDateTime createdAt;

      LocalDateTime updatedAt;

     Integer createdBy;
     Integer updatedBy;
     RoleDTO role;


}

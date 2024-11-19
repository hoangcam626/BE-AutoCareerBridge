package com.backend.autocarrerbridge.dto.AccountRespone;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UniversityDTO {
     Integer id;
     Integer logoImageId;
     String name;
     String website;
     Integer foundedYear;
     String email;
     String phone;
     String description;
     Integer status;
     LocalDate createdAt;
     LocalDate updatedAt;
     Integer createdBy;
     Integer updatedBy;
     Integer userAccountid;
     Integer locationid;


}

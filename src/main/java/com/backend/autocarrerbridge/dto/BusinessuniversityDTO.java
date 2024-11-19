package com.backend.autocarrerbridge.dto;

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
public class BusinessuniversityDTO {
     Integer id;
     Integer status;
     Integer statusConnected;
     LocalDate createdAt;
     LocalDate updatedAt;
     Integer createdBy;
     Integer updatedBy;
     Integer businessid;
     Integer universityid;


}

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
public class BusinessindustryDTO {
     Integer id;
     Integer status;
     LocalDate createAt;
     LocalDate updateAt;
     Integer createBy;
     Integer updateBy;
     Integer businessid;
     Integer industryid;


}

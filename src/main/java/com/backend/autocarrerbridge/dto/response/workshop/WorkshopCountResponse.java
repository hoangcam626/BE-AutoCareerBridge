package com.backend.autocarrerbridge.dto.response.workshop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkshopCountResponse {

  private String nameWorkshop;
  private long countApproved;
}

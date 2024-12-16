package com.backend.autocarrerbridge.dto.response.instructional;

import com.backend.autocarrerbridge.dto.response.abstractaudit.AbstractAuditResponse;
import com.backend.autocarrerbridge.entity.AbstractAudit;
import com.backend.autocarrerbridge.util.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@Builder
public class InstructionalResponse extends AbstractAuditResponse {

  private Integer id;

  private String name;

  private String gender;
  @JsonFormat(pattern = "dd/MM/yyyy") // Định dạng khi trả về dữ liệu
  private LocalDate dateOfBirth;

  private String email;

  private String address;

  private String instructionalCode;

  private Integer instructionalImageId;

  private String phone;

  private Integer universityId;

  private Integer userAccountId;

}

package com.backend.autocarrerbridge.dto.response.instructional;

import com.backend.autocarrerbridge.dto.response.abstractaudit.AbstractAuditResponse;
import java.time.LocalDate;
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

  private LocalDate dateOfBirth;

  private String email;

  private String address;

  private String instructionalCode;

  private Integer instructionalImageId;

  private String phone;

  private Integer universityId;

  private Integer userAccountId;

}

package com.backend.autocarrerbridge.dto.response.section;


import com.backend.autocarrerbridge.util.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SectionResponse {

  private Integer id;
  @NotBlank(message = "NAME_SECTION_NOT_BLANK")
  @Size(max = 30, message = "NAME_SECTION_NOT_LOGGER")
  private String name;
  @Size(max = 255, message = "DESCRIPTION")
  private String description;
  @NotNull(message = "STATUS_SECTION_NOT_NULL")
  private Status status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  @NotNull(message = "CREATED_BY_SECTION_NOT_NULL")
  private String createdBy;
  private String updatedBy;
  @NotNull(message = "UNIVERSITY_SECTION_NOT_NULL")
  private Integer universityId;

}

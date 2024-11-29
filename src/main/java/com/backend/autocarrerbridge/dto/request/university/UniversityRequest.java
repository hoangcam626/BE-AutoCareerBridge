package com.backend.autocarrerbridge.dto.request.university;

import static com.backend.autocarrerbridge.exception.ErrorCode.NAME_UNIVERSITY_NOT_BLANK;
import static com.backend.autocarrerbridge.util.Constant.FOUNDED_YEAR_UNIVERSITY_NOT_BLANK_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.NAME_UNIVERSITY_NOT_BLANK_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.NAME_UNIVERSITY_SIZE_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.PHONE_UNIVERSITY_NOT_BLANK_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.WEBSITE_UNIVERSITY_NOT_BLANK_MESSAGE;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UniversityRequest {

  Integer id;
  MultipartFile logoImageId;
  @NotBlank(message = NAME_UNIVERSITY_NOT_BLANK_MESSAGE)
  @Size(min = 10, max = 100, message = NAME_UNIVERSITY_SIZE_MESSAGE)
  String name;
  @NotBlank(message = WEBSITE_UNIVERSITY_NOT_BLANK_MESSAGE)
  String website;
  @NotBlank(message = FOUNDED_YEAR_UNIVERSITY_NOT_BLANK_MESSAGE)
  Integer foundedYear;
  @NotBlank(message = PHONE_UNIVERSITY_NOT_BLANK_MESSAGE)
  @Size(min = 9, max = 15)
  String phone;
  String description;


}

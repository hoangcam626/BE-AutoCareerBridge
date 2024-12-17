package com.backend.autocarrerbridge.dto.request.instructional;

import static com.backend.autocarrerbridge.util.Constant.INSTRUCTIONAL_ADRESS_NOT_BLANK_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.INSTRUCTIONAL_EMAIL_NOT_BLANK_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.INSTRUCTIONAL_EMAIL_REGEX_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.INSTRUCTIONAL_GENDER_NOT_BLANK_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.INSTRUCTIONAL_GENDER_REGEX_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.INSTRUCTIONAL_NAME_NOT_BANK_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.INSTRUCTIONAL_NAME_SIZE_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.INSTRUCTIONAL_PHONE_NOT_BLANK_MESSAGE;

import com.backend.autocarrerbridge.util.enums.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class InstructionalRequest{

    private Integer id;
    @NotBlank(message = INSTRUCTIONAL_NAME_NOT_BANK_MESSAGE)
    @Size(min = 2, max = 100, message = INSTRUCTIONAL_NAME_SIZE_MESSAGE)
    private String name;
    @NotBlank(message = INSTRUCTIONAL_GENDER_NOT_BLANK_MESSAGE)
    @Pattern(regexp = "Male|Female|Other", message = INSTRUCTIONAL_GENDER_REGEX_MESSAGE)
    private String gender;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;
    @NotBlank(message = INSTRUCTIONAL_EMAIL_NOT_BLANK_MESSAGE)
    @Email(message = INSTRUCTIONAL_EMAIL_REGEX_MESSAGE)
    private String email;
    @NotBlank(message = INSTRUCTIONAL_ADRESS_NOT_BLANK_MESSAGE)
    private String address;

    private MultipartFile instructionalImageId;

    private String instructionalCode;
    @NotBlank(message = INSTRUCTIONAL_PHONE_NOT_BLANK_MESSAGE)
    private String phone;

    private Integer universityId;

    private Integer userAccountId;

    private Status status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String createdBy;

    private String updatedBy;
}

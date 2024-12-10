package com.backend.autocarrerbridge.dto.request.instructional;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class InstructionalRequest {

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

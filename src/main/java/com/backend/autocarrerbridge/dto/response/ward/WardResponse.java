package com.backend.autocarrerbridge.dto.response.ward;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class WardResponse {
    private Integer id;

    private String fullName;
    @JsonIgnore
    private String fullNameEn;

    private String name;
    @JsonIgnore
    private String nameEn;
    @JsonIgnore
    private String codeName;
}

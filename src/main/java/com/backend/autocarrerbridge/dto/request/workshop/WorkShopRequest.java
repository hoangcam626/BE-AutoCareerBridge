package com.backend.autocarrerbridge.dto.request.workshop;

import static com.backend.autocarrerbridge.util.Constant.ADDRESS_DESCRIPTION;
import static com.backend.autocarrerbridge.util.Constant.DES_WORK_SHOP_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.TITLE_SIZE_MAX;
import static com.backend.autocarrerbridge.util.Constant.TITLE_SIZE_MIN;
import static com.backend.autocarrerbridge.util.Constant.TITLE_WORK_SHOP_MESSAGE;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import com.backend.autocarrerbridge.util.enums.State;
import com.backend.autocarrerbridge.util.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkShopRequest {

    private Integer id;
    @Size(max = 256, message = TITLE_SIZE_MAX)
    @Size(min = 10, message = TITLE_SIZE_MIN)
    @NotBlank(message = TITLE_WORK_SHOP_MESSAGE)
    private String title;

    @NotNull(message = DES_WORK_SHOP_MESSAGE)
    @Lob
    private String description;

    private Status status;
    private State statusBrowse;

    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private LocalDate expireDate;

    private MultipartFile imageWorkshop;
    private Integer idProvince;
    private Integer idDistrict;
    private Integer idWard;
    @Size(max = 128, message = ADDRESS_DESCRIPTION)
    private String addressDescription;
    private Integer universityId;
}

package com.backend.autocarrerbridge.dto.request.workshop;

import com.backend.autocarrerbridge.util.enums.State;
import com.backend.autocarrerbridge.util.enums.Status;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;


import static com.backend.autocarrerbridge.util.Constant.DATE_WORK_SHOP_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.DES_WORK_SHOP_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.TITLE_WORK_SHOP_MESSAGE;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkShopRequest {

    private Integer id;
    @NotBlank(message = TITLE_WORK_SHOP_MESSAGE)
    private String title;
    @NotNull(message = DES_WORK_SHOP_MESSAGE)
    private String description;
    private Status status;
    private State statusBrowse;

    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
    @NotBlank(message = DATE_WORK_SHOP_MESSAGE)
    private LocalDateTime startDate;
    @NotBlank(message = DATE_WORK_SHOP_MESSAGE)
    private LocalDateTime endDate;
    @NotBlank(message = DATE_WORK_SHOP_MESSAGE)
    private LocalDate expireDate;

    private MultipartFile imageWorkshop;
    private Integer locationId;
    private Integer universityId;
}

package com.backend.autocarrerbridge.dto.response.workshop;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class WorkshopPortalResponse {
    private Integer id;
    private String hostWorkshop;
    private Integer imageUniversity;
    private String title;
    private String description;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime endDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate expireDate;
    private Integer imageId;

    private String address;

    private String province;

    private String district;
    private String ward;

    private Long totalCompany;
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    public WorkshopPortalResponse(Integer id, String hostWorkshop, Integer imageUniversity, String title, String description, LocalDateTime startDate, LocalDateTime endDate, LocalDate expireDate, Integer imageId, String address, String province, String district, String ward, Long totalCompany, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.totalCompany = totalCompany;
        this.imageUniversity = imageUniversity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.hostWorkshop = hostWorkshop;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.expireDate = expireDate;
        this.imageId = imageId;
        this.address = address;
        this.province = province;
        this.district = district;
        this.ward = ward;
    }
}

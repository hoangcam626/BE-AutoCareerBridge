package com.backend.autocarrerbridge.dto.response.workshop;


import com.backend.autocarrerbridge.util.enums.State;
import com.backend.autocarrerbridge.util.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
public class WorkshopStateBusiness {

        private Integer id;
        private Integer universityId;
        private Integer logoImageUniversityId;
        private String nameUniversity;
        private String title;
        private String description;
        private Status status;
        private State statusBrowse;
        private State statusBusiness;
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        private LocalDateTime startDate;
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        private LocalDateTime endDate;
        @JsonFormat(pattern = "dd/MM/yyyy")
        private LocalDate expireDate;
        private String address;
        private String province;
        private String district;
        private String ward;
        private Integer workshopImageId;
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        private LocalDateTime createdAt;
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        private LocalDateTime updatedAt;
        public WorkshopStateBusiness(Integer id, Integer universityId, Integer logoImageUniversityId, String nameUniversity, String title, String description, Status status, State statusBrowse, State statusBusiness, LocalDateTime startDate, LocalDateTime endDate, LocalDate expireDate, Integer workshopImageId, String address, String province, String district, String ward,LocalDateTime createdAt,LocalDateTime updatedAt) {
                this.id = id;
                this.createdAt = createdAt;
                this.updatedAt = updatedAt;
                this.universityId = universityId;
                this.logoImageUniversityId = logoImageUniversityId;
                this.nameUniversity = nameUniversity;
                this.title = title;
                this.description = description;
                this.status = status;
                this.statusBrowse = statusBrowse;
                this.statusBusiness = statusBusiness;
                this.startDate = startDate;
                this.endDate = endDate;
                this.expireDate = expireDate;
                this.address = address;
                this.province = province;
                this.district = district;
                this.ward = ward;
                this.workshopImageId = workshopImageId;
        }
}


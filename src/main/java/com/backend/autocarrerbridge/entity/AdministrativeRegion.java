package com.backend.autocarrerbridge.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "administrative_regions")
public class AdministrativeRegion {

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "code_name")
    private String codeName;

    @Column(name = "code_name_en")
    private String codeNameEn;
}

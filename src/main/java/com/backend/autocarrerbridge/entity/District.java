package com.backend.autocarrerbridge.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "districts")
public class District {
    @Id
    @Column(name = "code", nullable = false)
    private Integer id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "full_name_en")
    private String fullNameEn;

    @Column(name = "name")
    private String name;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "code_name")
    private String codeName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "adminstrative_units_id", nullable = false)
    private AdministrativeUnit administrativeUnits;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "provinces_code", nullable = false)
    private Province provinces;

}
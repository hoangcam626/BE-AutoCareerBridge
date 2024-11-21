package com.backend.autocarrerbridge.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "provinces")
public class Province {
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
    @JoinColumn(name = "administrative_unit_id", nullable = false)
    private AdministrativeUnit administrativeUnits;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "administrative_region_id", nullable = false)
    private AdministrativeRegion administrativeRegions;

}
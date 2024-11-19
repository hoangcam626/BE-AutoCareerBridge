package com.backend.autocarrerbridge.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "business")
public class Business extends AbstractAudit{

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "tax_code", nullable = false)
    private String taxCode;

    @Column(name = "company_size")
    private String companySize;

    @Column(name = "website")
    private String website;

    @Column(name = "found_year")
    private Integer foundYear;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "description")
    private String description;

    @Column(name = "business_image_id")
    private Integer businessImageId;

    @Column(name = "license_image_id")
    private Integer licenseImageId;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = true)
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_account_id", nullable = false)
    private UserAccount userAccount;

}
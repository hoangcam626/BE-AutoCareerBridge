package com.backend.autocarrerbridge.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "business")
public class Business extends AbstractAudit {

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
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_account_id", nullable = false)
    private UserAccount userAccount;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Business business = (Business) object;
        return Objects.equals(id, business.id) && Objects.equals(name, business.name) && Objects.equals(taxCode, business.taxCode) && Objects.equals(companySize, business.companySize) && Objects.equals(website, business.website) && Objects.equals(foundYear, business.foundYear) && Objects.equals(email, business.email) && Objects.equals(phone, business.phone) && Objects.equals(description, business.description) && Objects.equals(businessImageId, business.businessImageId) && Objects.equals(licenseImageId, business.licenseImageId) && Objects.equals(location, business.location) && Objects.equals(userAccount, business.userAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, name, taxCode, companySize, website, foundYear, email, phone, description, businessImageId, licenseImageId, location, userAccount);
    }
}

package com.backend.autocarrerbridge.entity;

import java.util.Objects;

import jakarta.persistence.*;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "university")
public class University extends AbstractAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "logo_image_id")
    private Integer logoImageId;

    @Column(name = "name")
    private String name;

    @Column(name = "website")
    private String website;

    @Column(name = "founded_year")
    private Integer foundedYear;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_account_id", nullable = false)
    private UserAccount userAccount;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        University that = (University) object;
        return Objects.equals(id, that.id)
                && Objects.equals(logoImageId, that.logoImageId)
                && Objects.equals(name, that.name)
                && Objects.equals(website, that.website)
                && Objects.equals(foundedYear, that.foundedYear)
                && Objects.equals(email, that.email)
                && Objects.equals(phone, that.phone)
                && Objects.equals(description, that.description)
                && Objects.equals(userAccount, that.userAccount)
                && Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                id,
                logoImageId,
                name,
                website,
                foundedYear,
                email,
                phone,
                description,
                userAccount,
                location);
    }
}

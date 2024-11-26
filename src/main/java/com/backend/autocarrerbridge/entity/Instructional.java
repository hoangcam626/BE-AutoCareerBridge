package com.backend.autocarrerbridge.entity;

import java.time.LocalDate;
import java.util.Objects;

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

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "instructional")
public class Instructional extends AbstractAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    private String gender;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "instructional_code", nullable = false)
    private String instructionalCode;

    @Column(name = "instructional_image_id")
    private Integer instructionalImageId;

    @Column(name = "phone")
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "university_id", nullable = false)
    private University university;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_account_id", nullable = false)
    private UserAccount userAccount;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Instructional that = (Instructional) object;
        return Objects.equals(id, that.id)
                && Objects.equals(name, that.name)
                && Objects.equals(gender, that.gender)
                && Objects.equals(dateOfBirth, that.dateOfBirth)
                && Objects.equals(email, that.email)
                && Objects.equals(address, that.address)
                && Objects.equals(instructionalCode, that.instructionalCode)
                && Objects.equals(instructionalImageId, that.instructionalImageId)
                && Objects.equals(phone, that.phone)
                && Objects.equals(university, that.university)
                && Objects.equals(userAccount, that.userAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                id,
                name,
                gender,
                dateOfBirth,
                email,
                address,
                instructionalCode,
                instructionalImageId,
                phone,
                university,
                userAccount);
    }
}

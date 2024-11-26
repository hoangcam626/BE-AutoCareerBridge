package com.backend.autocarrerbridge.entity;

import java.util.Objects;

import jakarta.persistence.*;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "sub_admin")
public class SubAdmin extends AbstractAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "subadmin_code")
    private String subAdminCode;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    private String gender;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "subadmin_image_id")
    private Integer subAdminImageId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_account_id", nullable = false)
    private UserAccount userAccount;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        SubAdmin subAdmin = (SubAdmin) object;
        return Objects.equals(id, subAdmin.id)
                && Objects.equals(subAdminCode, subAdmin.subAdminCode)
                && Objects.equals(name, subAdmin.name)
                && Objects.equals(gender, subAdmin.gender)
                && Objects.equals(email, subAdmin.email)
                && Objects.equals(phone, subAdmin.phone)
                && Objects.equals(address, subAdmin.address)
                && Objects.equals(subAdminImageId, subAdmin.subAdminImageId)
                && Objects.equals(userAccount, subAdmin.userAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(), id, subAdminCode, name, gender, email, phone, address, subAdminImageId, userAccount);
    }
}

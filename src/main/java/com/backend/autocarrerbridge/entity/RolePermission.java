package com.backend.autocarrerbridge.entity;

import java.util.Objects;

import jakarta.persistence.*;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "role_permission")
public class RolePermission extends AbstractAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "permission_id", nullable = false)
    private Permission permission;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        RolePermission that = (RolePermission) object;
        return Objects.equals(id, that.id)
                && Objects.equals(role, that.role)
                && Objects.equals(permission, that.permission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, role, permission);
    }
}

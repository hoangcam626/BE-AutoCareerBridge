package com.backend.autocarrerbridge.entity;

import jakarta.persistence.*;

import lombok.*;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
@Table(name = "role")
public class Role extends AbstractAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Role role = (Role) object;
        return Objects.equals(id, role.id) && Objects.equals(name, role.name) && Objects.equals(description, role.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, name, description);
    }
}

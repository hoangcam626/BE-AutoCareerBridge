package com.backend.autocarrerbridge.entity;

import jakarta.persistence.*;

import lombok.*;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "major")
public class Major extends AbstractAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "number_student")
    private Integer numberStudent;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "section_id", nullable = false)
    private Section section;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Major major = (Major) object;
        return Objects.equals(id, major.id) && Objects.equals(code, major.code) && Objects.equals(name, major.name) && Objects.equals(numberStudent, major.numberStudent) && Objects.equals(description, major.description) && Objects.equals(section, major.section);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, code, name, numberStudent, description, section);
    }
}

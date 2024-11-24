package com.backend.autocarrerbridge.entity;

import jakarta.persistence.*;

import lombok.*;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
@Table(name = "image")
public class Image extends AbstractAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "file_name")
    private String filename;

    @Column(name = "file_type")
    private String type;
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Image image = (Image) object;
        return Objects.equals(id, image.id) && Objects.equals(filename, image.filename) && Objects.equals(type, image.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, filename, type);
    }
}

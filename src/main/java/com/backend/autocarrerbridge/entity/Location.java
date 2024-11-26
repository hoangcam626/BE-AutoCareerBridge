package com.backend.autocarrerbridge.entity;

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
@Table(name = "location")
public class Location extends AbstractAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "provinces_code", nullable = false)
    private Province provinces;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "districts_code", nullable = false)
    private District districts;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wards_code", nullable = false)
    private Ward wards;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Location location = (Location) object;
        return Objects.equals(id, location.id)
                && Objects.equals(description, location.description)
                && Objects.equals(provinces, location.provinces)
                && Objects.equals(districts, location.districts)
                && Objects.equals(wards, location.wards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, description, provinces, districts, wards);
    }
}

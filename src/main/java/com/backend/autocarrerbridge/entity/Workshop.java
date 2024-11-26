package com.backend.autocarrerbridge.entity;

import com.backend.autocarrerbridge.util.enums.State;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "workshop")
public class Workshop extends AbstractAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "workshop_image_id")
    private Integer workshopImageId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "expire_date")
    private LocalDate expireDate;

    @Column(name = "status_browse")
    private State statusBrowse;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "university_id", nullable = false)
    private University university;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Workshop workshop = (Workshop) object;
        return Objects.equals(id, workshop.id)
                && Objects.equals(workshopImageId, workshop.workshopImageId)
                && Objects.equals(title, workshop.title)
                && Objects.equals(description, workshop.description)
                && Objects.equals(expireDate, workshop.expireDate)
                && statusBrowse == workshop.statusBrowse
                && Objects.equals(startDate, workshop.startDate)
                && Objects.equals(endDate, workshop.endDate)
                && Objects.equals(university, workshop.university)
                && Objects.equals(location, workshop.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                id,
                workshopImageId,
                title,
                description,
                expireDate,
                statusBrowse,
                startDate,
                endDate,
                university,
                location);
    }
}

package com.backend.autocarrerbridge.entity;

import jakarta.persistence.*;

import com.backend.autocarrerbridge.util.enums.State;

import lombok.*;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "workshop_business")
public class WorkshopBusiness extends AbstractAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "status_connected")
    private State statusConnected;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workshop_id", nullable = false)
    private Workshop workshop;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        WorkshopBusiness that = (WorkshopBusiness) object;
        return Objects.equals(id, that.id) && statusConnected == that.statusConnected && Objects.equals(workshop, that.workshop) && Objects.equals(business, that.business);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, statusConnected, workshop, business);
    }
}

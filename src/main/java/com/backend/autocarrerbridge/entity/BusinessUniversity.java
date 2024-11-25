package com.backend.autocarrerbridge.entity;

import jakarta.persistence.*;

import com.backend.autocarrerbridge.util.enums.State;

import lombok.*;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "business_university")
public class BusinessUniversity extends AbstractAudit {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "status_connected")
    private State statusConnected;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "university_id", nullable = false)
    private University university;
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        BusinessUniversity that = (BusinessUniversity) object;
        return Objects.equals(id, that.id) && statusConnected == that.statusConnected && Objects.equals(business, that.business) && Objects.equals(university, that.university);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, statusConnected, business, university);
    }
}

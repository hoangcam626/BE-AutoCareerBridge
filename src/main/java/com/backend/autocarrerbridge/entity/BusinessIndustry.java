package com.backend.autocarrerbridge.entity;

import jakarta.persistence.*;

import lombok.*;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "business_industry")
public class BusinessIndustry extends AbstractAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "industry_id", nullable = false)
    private Industry industry;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        BusinessIndustry that = (BusinessIndustry) object;
        return Objects.equals(id, that.id) && Objects.equals(business, that.business) && Objects.equals(industry, that.industry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, business, industry);
    }
}

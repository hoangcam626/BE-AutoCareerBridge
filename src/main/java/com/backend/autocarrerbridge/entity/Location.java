package com.backend.autocarrerbridge.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "location")
public class Location extends AbstractAudit{

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

}
package com.backend.autocarrerbridge.entity;

import java.util.Objects;

import jakarta.persistence.*;

import com.backend.autocarrerbridge.util.enums.State;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "university_job")
public class UniversityJob extends AbstractAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "status_connected")
    private State statusConnected;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "university_id", nullable = false)
    private University university;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        UniversityJob that = (UniversityJob) object;
        return Objects.equals(id, that.id)
                && statusConnected == that.statusConnected
                && Objects.equals(university, that.university)
                && Objects.equals(job, that.job);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, statusConnected, university, job);
    }
}

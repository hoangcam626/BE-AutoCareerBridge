package com.backend.autocarrerbridge.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "notification")
public class Notification extends AbstractAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "message")
    private String message;

    @Column(name = "status_read")
    private Integer statusRead;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Notification that = (Notification) object;
        return Objects.equals(id, that.id)
                && Objects.equals(message, that.message)
                && Objects.equals(statusRead, that.statusRead);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, message, statusRead);
    }
}

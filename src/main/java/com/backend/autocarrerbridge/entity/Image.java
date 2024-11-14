package com.backend.autocarrerbridge.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

@Entity
@Table(name = "image")
public class Image extends AbstractAudit {

    @Column(name = "file_name")
    private String filename;

    @Column(name = "file_type")
    private String type;
}

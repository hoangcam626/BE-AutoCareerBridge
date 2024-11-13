package com.backend.autocarrerbridge.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "image")
public class Image extends AbstractAudit {

    @Column(name = "file_name")
    private String filename;

    @Column(name = "file_type")
    private String type;
}

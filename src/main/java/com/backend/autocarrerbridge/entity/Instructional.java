package com.backend.autocarrerbridge.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "instructional")
public class Instructional extends AbstractAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    private String gender;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "instructional_code", nullable = false)
    private String instructionalCode;

    @Column(name = "instructional_image_id")
    private Integer instructionalImageId;

    @Column(name = "phone")
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "university_id", nullable = false)
    private University university;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_account_id", nullable = false)
    private UserAccount userAccount;
}

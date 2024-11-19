package com.backend.autocarrerbridge.entity;

import jakarta.persistence.*;
import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "user_account")
public class UserAccount extends AbstractAudit{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;
    @Column(name = "refresh_token" ,length = 512)
    private String refreshToken;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

}
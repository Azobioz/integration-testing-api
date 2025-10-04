package com.azobioz.api.module;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Table(name = "revoked_tokens")
@Entity
public class RevokeToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    private Date expirationDate;
}

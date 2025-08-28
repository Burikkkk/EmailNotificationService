package com.example.authservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(name="password_hash", nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String email;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private Role role;
}

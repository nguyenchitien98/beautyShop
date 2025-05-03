package com.tien.entity;

import com.tien.security.entity.RefreshToken;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String fullName;

    private String avatar;

    private String email;

    private String phoneNumber;

    private String address;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role; // CUSTOMER / ADMIN

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)  // Quan hệ một-một với RefreshToken
    private RefreshToken refreshToken;  // Mỗi User có một RefreshToken duy nhất

//    private LocalDateTime createdAt;
//
//    private LocalDateTime updatedAt;

}

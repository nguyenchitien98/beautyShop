package com.tien.entity;

import com.tien.security.entity.RefreshToken;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false) //  không muốn tính id từ BaseEntity vào equals/hashCode
public class User extends BaseEntity{

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings = new ArrayList<>();
}

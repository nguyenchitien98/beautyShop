package com.tien.security.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tien.entity.BaseEntity;
import com.tien.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Table(name = "refresh_tokens")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false) //  không muốn tính id từ BaseEntity vào equals/hashCode
public class RefreshToken extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)  // Quan hệ một-một với User
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, unique = true)  // Đảm bảo user_id là khóa ngoại và unique
    private User user;  // Liên kết với User entity

    private Date expirationDate;
}

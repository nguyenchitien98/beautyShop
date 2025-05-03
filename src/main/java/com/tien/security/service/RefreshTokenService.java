package com.tien.security.service;

import com.tien.entity.User;
import com.tien.repository.UserRepository;
import com.tien.security.entity.RefreshToken;
import com.tien.security.repository.RefreshTokenRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

@Service
public class RefreshTokenService {
    private static final String REFRESH_SECRET = "gPq1$w&9Xn*E@bNz5uWmVkJz!3rTbHsYq0LpCz9dVb#4nJq8pEfRhUgZnMqAoByH";
    private static final long REFRESH_TOKEN_VALIDITY = 7 * 24 * 60 * 60 * 1000; // 7 ngày

    private final SecretKey key = Keys.hmacShaKeyFor(REFRESH_SECRET.getBytes(StandardCharsets.UTF_8));
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    public String generateRefreshToken(Long userId, String email) {
//        return Jwts.builder()
//                .claim("userId", userId)
//                .claim("email", email)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY))
//                .signWith(key, SignatureAlgorithm.HS512)
//                .compact();

        // Lấy đối tượng User từ database
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        String token = Jwts.builder()
                .claim("userId", userId)
                .claim("email", email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY))
                .signWith(key)
                .compact();

        // Kiểm tra và lưu refresh token vào DB
        Optional<RefreshToken> existingRefreshToken = refreshTokenRepository.findByUserId(userId);
        if (existingRefreshToken.isPresent()) {
            // Nếu có refresh token cũ, cập nhật lại token
            RefreshToken refreshToken = existingRefreshToken.get();
            refreshToken.setToken(token);
            refreshToken.setExpirationDate(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY));
            refreshTokenRepository.save(refreshToken);
        } else {
            // Nếu chưa có refresh token, tạo mới
            RefreshToken refreshToken = new RefreshToken();
            refreshToken.setToken(token);
            refreshToken.setUser(user);
            refreshToken.setExpirationDate(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY));
            refreshTokenRepository.save(refreshToken);
        }
        return token;
    }

    public boolean validateRefreshToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public Long getUserIdFromRefreshToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("userId", Long.class);
    }

    public String getEmailFromRefreshToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("email", String.class);
    }


    public Optional<RefreshToken> findByUserId(Long userId) {
       return refreshTokenRepository.findByUserId(userId);  // Xóa refresh token của người dùng
    }

    public RefreshToken updateRefreshToken(RefreshToken refreshToken) {
        return refreshTokenRepository.save(refreshToken);  // Xóa refresh token của người dùng
    }

}

package com.tien.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
    private static final String secretKeyString = "gPq1$w&9Xn*E@bNz5uWmVkJz!3rTbHsYq0LpCz9dVb#4nJq8pEfRhUgZnMqTwXsY";
    private final SecretKey jwtSecretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes(StandardCharsets.UTF_8));

    private final long jwtExpirationMs = 86400000; // 1 day

    // Phương thức tạo access token
    public String generateToken(Long userId, String email) {
        return Jwts.builder()
                .subject(email)
                .claim("userId", userId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(jwtSecretKey)
                .compact();
    }

    // Phương thức lấy Claims từ token
    public Claims getClaimsFromToken(String token) {
        return Jwts.parser()  // Sử dụng parser thay vì parserBuilder (vì parseBuilder was changed to parser() in 0.12 Version.)
                .verifyWith(jwtSecretKey)      // Cung cấp secret key
                .build().parseSignedClaims(token).getPayload();  // Giải mã token và lấy claims
    }

    // Phương thức lấy email từ token
    public String getEmailFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    // Phương thức lấy userId từ token
    public Long getUserIdFromToken(String token) {
        Claims claims = getClaimsFromToken(token); // parse 1 lần duy nhất
        return claims.get("userId", Long.class);
//        return getClaimsFromToken(token).get("userId", Long.class);
    }

    // Kiểm tra xem token có hết hạn không
    public boolean isTokenExpired(String token) {
        Date expiration = getClaimsFromToken(token).getExpiration();
        return expiration.before(new Date());
    }
}

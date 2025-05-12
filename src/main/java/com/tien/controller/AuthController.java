package com.tien.controller;

import com.tien.dto.request.LoginRequest;
import com.tien.dto.request.UserRegisterRequest;
import com.tien.payload.ApiCode;
import com.tien.payload.ApiResponse;
import com.tien.payload.ApiResponseBuilder;
import com.tien.security.entity.RefreshToken;
import com.tien.security.model.CustomUserDetails;
import com.tien.entity.Role;
import com.tien.entity.User;
import com.tien.repository.UserRepository;
import com.tien.security.jwt.JwtUtil;
import com.tien.security.repository.RefreshTokenRepository;
import com.tien.security.service.CustomUserDetailsService;
import com.tien.security.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponseBuilder.error(ApiCode.EMAIL_ALREADY_EXISTS));
//            return ResponseEntity.badRequest().body("Email already exists!");
        }

        User user = User.builder()
                .username(request.getName().substring(request.getName().lastIndexOf(" ") +1 ))
                .fullName(request.getName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .address(request.getAddress())
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return ResponseEntity.ok(ApiResponseBuilder.success(ApiCode.REGISTER_SUCCESS));
//        return ResponseEntity.ok(ApiResponseBuilder.success("User registered successfully!"));
//        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@Valid @RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
//            return jwtUtil.generateToken(userDetails.getId(), userDetails.getUsername());

            // Kiểm tra xem người dùng đã có refresh token chưa
            Optional<RefreshToken> existingRefreshToken = refreshTokenService.findByUserId(userDetails.getId());

            String refreshToken;

            if (existingRefreshToken.isPresent() && existingRefreshToken.get().getToken() != null) {
                // Nếu người dùng đã có refresh token hợp lệ, trả về token cũ
                refreshToken = existingRefreshToken.get().getToken();
            }  else {
                // Nếu chưa có, tạo mới refresh token và lưu vào DB
                refreshToken = refreshTokenService.generateRefreshToken(userDetails.getId(), userDetails.getEmail());
            }
            String accessToken = jwtUtil.generateToken(userDetails.getId(), userDetails.getEmail());

            Object data = Map.of(
                    "accessToken", accessToken,
                    "refreshToken", refreshToken,
                    "id", userDetails.getId(),
                    "email", userDetails.getEmail()
            );
            return ResponseEntity.ok(ApiResponseBuilder.success(ApiCode.LOGIN_SUCCESS,data));
//            return ResponseEntity.ok(
//                    ApiResponse.builder()
//                    .success(true)
//                    .message("Login successful")
//                    .data(data)
//                    .build());
        } catch (AuthenticationException e) {
            throw new RuntimeException("Login thất bại: " + e.getMessage());
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        if (!refreshTokenService.validateRefreshToken(refreshToken)) {
            return ResponseEntity.badRequest().body("Invalid refresh token");
        }

        Long userId = refreshTokenService.getUserIdFromRefreshToken(refreshToken);
        String email = refreshTokenService.getEmailFromRefreshToken(refreshToken);

        // Tạo mới access token
        String newAccessToken = jwtUtil.generateToken(userId, email);
        Object data = Map.of("accessToken", newAccessToken);

        return ResponseEntity.ok(ApiResponseBuilder.success(data));
//        return ResponseEntity.ok(Map.of(
//                "accessToken", newAccessToken
//        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7).trim(); // cắt "Bearer " và .trim() cho chắc chắn
            Long userId = jwtUtil.getUserIdFromToken(token);

            // Lấy đối tượng RefreshToken của người dùng
            RefreshToken refreshToken = refreshTokenService.findByUserId(userId)
                    .orElseThrow(() -> new RuntimeException("RefreshToken không tồn tại"));
            // Cập nhật RefreshToken thành null
            refreshToken.setToken(null); // Set token thành null
            refreshToken.setExpirationDate(null); // Set expirationDate thành null

            // Lưu lại sự thay đổi
            refreshTokenService.updateRefreshToken(refreshToken);

        }

        return ResponseEntity.ok(ApiResponseBuilder.success("Logout successful"));
//        return ResponseEntity.ok(Map.of("message", "Logout successful"));
    }
}

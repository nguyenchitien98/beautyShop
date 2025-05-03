package com.tien.security.filter;

import com.tien.security.model.CustomUserDetails;
import com.tien.security.jwt.JwtUtil;
import com.tien.security.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
// OncePerRequestFilter: Là class của Spring Security, đảm bảo mỗi request chỉ lọc một lần duy nhất.
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7).trim(); // bỏ "Bearer "
            System.out.println(token);
            try {
                if (!jwtUtil.isTokenExpired(token)) { // Token còn sống
                    String email = jwtUtil.getEmailFromToken(token);
                    CustomUserDetails userDetails = userDetailsService.loadUserByUsername(email);

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                // Nếu token lỗi (ví dụ không parse được) thì bỏ qua, không authenticate
                System.out.println("Authentication failed: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}

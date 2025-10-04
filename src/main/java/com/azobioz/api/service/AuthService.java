package com.azobioz.api.service;

import com.azobioz.api.dto.AuthResponse;
import com.azobioz.api.dto.LoginRequest;
import com.azobioz.api.dto.RegisterRequest;
import com.azobioz.api.module.User;
import com.azobioz.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    public void register(RegisterRequest request) {
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if(!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Wrong credentials");
        }
        String accessToken = jwtService.generateAccessToken(user.getEmail());
        String refreshToken = jwtService.generateRefreshToken(user.getEmail());

        jwtService.setCurrentAccessToken(accessToken);

        return new AuthResponse(user.getId(), accessToken, refreshToken);
    }

    public AuthResponse refresh(String refreshToken) {
        if (jwtService.isTokenExpired(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }
        String email = jwtService.extractEmail(refreshToken);
        User user = userRepository.getUserByEmail(email);
        String newAccessToken = jwtService.generateAccessToken(email);
        return new AuthResponse(user.getId(), newAccessToken, refreshToken);
    }
}

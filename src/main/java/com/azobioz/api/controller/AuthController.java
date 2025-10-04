package com.azobioz.api.controller;

import com.azobioz.api.dto.AuthResponse;
import com.azobioz.api.dto.LoginRequest;
import com.azobioz.api.dto.RefreshRequest;
import com.azobioz.api.dto.RegisterRequest;
import com.azobioz.api.service.AuthService;
import com.azobioz.api.service.JWTService;
import com.azobioz.api.service.RevokedTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final RevokedTokenService revokedTokenService;
    private final JWTService jWTService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("User is registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request)  {
        AuthResponse response = authService.login(request);
        jWTService.setCurrentAccessToken(response.accessToken());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshRequest request) {
        AuthResponse response = authService.refresh(request.refreshToken());
        jWTService.setCurrentAccessToken(response.accessToken());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader(value = "Authorization", required = false) String authHeader,
                                         @RequestBody(required = false) String refreshToken) {

        jWTService.setCurrentAccessToken(null);
//        revokedTokenService.revokeToken(refreshToken);

        return ResponseEntity.ok("Successfully logged out");
    }



}

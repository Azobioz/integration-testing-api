package com.azobioz.api.service;

import com.azobioz.api.module.RevokeToken;
import com.azobioz.api.repository.RevokedTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class RevokedTokenService {
    private final RevokedTokenRepository revokedTokenRepository;
    private final JWTService jwtService;

    public void revokeToken(String accessToken) {
        Date expiration = jwtService.extractExpiration(accessToken);
        RevokeToken revokeToken = new RevokeToken();
        revokeToken.setToken(accessToken);
        revokeToken.setExpirationDate(expiration);
        revokedTokenRepository.save(revokeToken);
    }

    public boolean isTokenRevoked(String token) {
        return revokedTokenRepository.existsByToken(token);
    }

}

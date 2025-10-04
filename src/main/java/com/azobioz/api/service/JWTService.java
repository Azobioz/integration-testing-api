package com.azobioz.api.service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JWTService {
    private final Key key;
    @Value("${token.expiration.access}")
    private long accessTokenExpiration;
    @Value("${token.expiration.refresh}")
    private long refreshTokenExpiration;
    private final TokenStoreService tokenStore;

    public JWTService(@Value("${SECRET_KEY}") String secret, TokenStoreService tokenStore) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.tokenStore = tokenStore;
    }

    public String generateAccessToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Date extractExpiration(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }

    public boolean isTokenExpired(String token) {
        try {
            Date expiration = extractExpiration(token);
            return expiration.before(new Date()); //false - срок не истек
        }
        catch (JwtException | IllegalArgumentException e) {
            return true;
        }
    }

    public void setCurrentAccessToken(String token) {
        tokenStore.setAccessToken(token);
    }

    public String getCurrentAccessToken() {
        return tokenStore.getAccessToken();
    }
}

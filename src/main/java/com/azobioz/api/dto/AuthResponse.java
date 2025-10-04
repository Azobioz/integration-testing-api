package com.azobioz.api.dto;

public record AuthResponse (Long id, String accessToken, String refreshToken) {
}

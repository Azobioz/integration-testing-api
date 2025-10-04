package com.azobioz.api.dto;

import com.azobioz.api.module.Order;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record UserResponse (Long id, String name, String email, LocalDateTime createdAt) {
}

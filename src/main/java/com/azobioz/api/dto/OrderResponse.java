package com.azobioz.api.dto;

import java.util.List;

public record OrderResponse (Long id, UserResponseForOrder user, List<ProductResponse> products) {
}

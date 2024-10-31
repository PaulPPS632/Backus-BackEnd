package com.example.backus.models.dto.auth;

import lombok.Builder;

@Builder
public record AuthResponse(
        String token,
        String username,
        String rol
) {
}

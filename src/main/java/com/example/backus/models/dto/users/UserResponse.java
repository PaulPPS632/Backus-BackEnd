package com.example.backus.models.dto.users;

import lombok.Builder;

@Builder
public record UserResponse(
        Long id,
        String username,
        String name,
        String adress,
        String phone,
        String document,
        RolResponse rol
) {
}

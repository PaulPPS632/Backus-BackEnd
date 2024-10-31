package com.example.backus.models.dto.users;

import lombok.Builder;

@Builder
public record UserAuthDto(
        Long id,
        String username,
        String password,
        String name,
        String adress,
        String phone,
        String document,
        String rol
) {
}

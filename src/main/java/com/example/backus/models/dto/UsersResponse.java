package com.example.backus.models.dto;

import lombok.Builder;

@Builder
public record UsersResponse(
        Long id,
        String name,
        String adress,
        String phone,
        String document,
        String email,
        String passwordtoken,
        RolesResponse rol
) {
}

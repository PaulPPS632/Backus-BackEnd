package com.example.backus.models.dto;

public record UsersResponse(
        Long id,
        String name,
        String adress,
        String phone,
        String document,
        String email,
        RolesResponse rol
) {
}

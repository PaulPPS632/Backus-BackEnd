package com.example.backus.models.dto;

public record UsersRequest(
        Long id,
        String name,
        String adress,
        String phone,
        String document,
        String email,
        Long RolId
) {
}

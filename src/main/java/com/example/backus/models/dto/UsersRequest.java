package com.example.backus.models.dto;

public record UsersRequest(
        Long id,
        String username,
        String name,
        String adress,
        String phone,
        String document,
        String password,
        Long RolId
) {
}

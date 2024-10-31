package com.example.backus.models.dto.users;

import lombok.Builder;

@Builder
public record UserResponseV2 (
    Long id,
    boolean regist,
    String email,
    String username,
    RolResponse rol
){}

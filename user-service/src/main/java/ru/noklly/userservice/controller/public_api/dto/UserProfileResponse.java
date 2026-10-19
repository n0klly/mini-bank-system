package ru.noklly.userservice.controller.public_api.dto;

import ru.noklly.userservice.entity.UserRole;

import java.time.LocalDateTime;

public record UserProfileResponse (
    Long id,
    String email,
    String name,
    UserRole role,
    LocalDateTime createdAt
) {}

package com.accenture.service.dto;

public record AdminResponseDto (
        long id,
        String nom,
        String prenom,
        String email,
        String password,
        String fonction
) {
}

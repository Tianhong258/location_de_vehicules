package com.accenture.service.dto.utilisateur;

public record AdminResponseDto (
        long id,
        String nom,
        String prenom,
        String email,
        String fonction
) {
}

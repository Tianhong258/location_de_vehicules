package com.accenture.service.dto.utilisateurDto;

public record AdminResponseDto (
        long id,
        String nom,
        String prenom,
        String email,
        String fonction
) {
}

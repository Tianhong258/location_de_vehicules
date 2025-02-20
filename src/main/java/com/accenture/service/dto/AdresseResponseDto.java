package com.accenture.service.dto;

public record AdresseResponseDto(
        long id,
        String rue,
        String codePostal,
        String ville
) {
}

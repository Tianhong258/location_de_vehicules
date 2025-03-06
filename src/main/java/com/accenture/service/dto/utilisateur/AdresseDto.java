package com.accenture.service.dto.utilisateur;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Détails de la demande d'adresse")
public record AdresseDto(
        @Schema(description = "Rue de l'adresse du client", example = "33 Rue Victor Hugo")
        @NotBlank(message = "La rue est obligatoire")
        String rue,

        @Schema(description = "Code postal de l'adresse du client", example = "44000")
        @NotBlank(message = "Le code postal est obligatoire")
        String codePostal,

        @Schema(description = "Ville de l'adresse du client", example = "Nantes")
        @NotBlank(message = "La ville est obligatoire")
        String ville

) {
}

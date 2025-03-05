package com.accenture.service.dto.utilisateur;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "Détails de la demande d'administrateur")
public record AdminRequestDto (
        @Schema(description = "Nom de l'administrateur", example = "Huang")
        @NotBlank(message = "Le nom est obligatoire")
        @Size(min = 3, max = 20, message = "Le nom doit être entre 3 et 20 caractères")
        String nom,

        @Schema(description = "Prénom de l'administrateur", example = "Tanya")
        @NotBlank(message = "Le prénom est obligatoire")
        @Size(min = 3, max = 20, message = "Le prénom doit être entre 3 et 20 caractères")
        String prenom,

        @Schema(description = "Email de l'administrateur", example = "tanyaAdmin@gamil.com")
        @NotNull(message = "L'email est obligatoire")
        @Email(message = "Le format du mail est invalide")
        String email,

        @Schema(description = "Mot de passe de l'administrateur", example = "345Tanya@")
        @NotBlank(message = "Le mot de passe est obligatoire")
        @Size(min = 8, max = 16, message = "Le mot de passe doit être entre 8 et 16 caractères")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[&\\#@\\-_%§]).{6,}$",
                message = "Le mot de passe doit contenir au moins une lettre majuscule, une lettre minuscule, et un des symboles suivants : & # @ - _ §")
        String password,

        @Schema(description = "Fonction de l'administrateur", example = "CEO")
        @NotBlank(message = "La fonction est obligatoire")
        @Size(min = 3, max = 20, message = "La fonction doit être entre 3 et 20 caractères")
        String fonction

) {
}

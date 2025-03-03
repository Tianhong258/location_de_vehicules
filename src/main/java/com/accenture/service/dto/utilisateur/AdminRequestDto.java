package com.accenture.service.dto.utilisateur;

import jakarta.validation.constraints.*;

public record AdminRequestDto (
        @NotBlank
        @Size(min=3, max=20, message = "Le nom doit être entre 3 et 20 caractères")
        String nom,
        @NotBlank
        @Size(min=3, max=20, message = "Le prénom doit être entre 3 et 20 caractères")
        String prenom,
        @NotNull
        @Email(message = "Le format du mail est invalide")
        String email,
        @NotBlank
        @Size(min=8, max=16, message = "Le mot de passe doit être entre 8 et 16 caractères")
        //TODO: vérifier c'est quoi tout ça
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[&\\#@\\-_%§]).{6,}$",
                message = "Le mot de passe doit contenir au moins une lettre majuscule, une lettre minuscule, et un des symboles suivants : & # @ - _ §"
        )
        String password,
        @NotBlank
        @Size(min=3, max=20, message = "La fonction doit être entre 3 et 20 caractères")
        String fonction

) {
}

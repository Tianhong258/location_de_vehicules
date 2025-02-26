package com.accenture.service.dto.utilisateurDto;

import com.accenture.model.Permis;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record ClientRequestDto(
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
        @NotNull
        AdresseDto adresse,
        @NotNull
        @Past(message = "La date de naissance est invalide")
        LocalDate dateNaissance,
        List<Permis> listePermis
        ) {
}

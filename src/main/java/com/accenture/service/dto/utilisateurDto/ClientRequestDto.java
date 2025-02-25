package com.accenture.service.dto.utilisateurDto;

import com.accenture.model.Permis;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record ClientRequestDto(
        @Size(min=3, max=20, message = "Le nom doit être entre 3 et 20 caractères")
        String nom,
        @Size(min=3, max=20, message = "Le prénom doit être entre 3 et 20 caractères")
        String prenom,
        @Email(message = "Le format du mail est invalide")
        String email,
        @Size(min=8, max=16, message = "Le mot de passe doit être entre 8 et 16 caractères")
        //TODO: vérifier c'est quoi tout ça
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[&\\#@\\-_%§]).{6,}$",
                message = "Le mot de passe doit contenir au moins une lettre majuscule, une lettre minuscule, et un des symboles suivants : & # @ - _ §"
        )
        String password,
        AdresseDto adresse,
        @Past(message = "La date de naissance est invalide")
        LocalDate dateNaissance,
        List<Permis> listePermis
        ) {
}

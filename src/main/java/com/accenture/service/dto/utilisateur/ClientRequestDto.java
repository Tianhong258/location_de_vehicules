package com.accenture.service.dto.utilisateur;

import com.accenture.model.Permis;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;
@Schema(description = "Détails de la demande de client")
public record ClientRequestDto(
        @Schema(description = "Nom du client", example = "Huang")
        @NotBlank(message = "Le nom est obligatoire")
        @Size(min = 3, max = 20, message = "Le nom doit être entre 3 et 20 caractères")
        String nom,

        @Schema(description = "Prénom du client", example = "Jaqen")
        @NotBlank(message = "Le prénom est obligatoire")
        @Size(min = 3, max = 20, message = "Le prénom doit être entre 3 et 20 caractères")
        String prenom,

        @Schema(description = "Email du client", example = "jaqenClient@gmail.com")
        @NotNull(message = "L'email est obligatoire")
        @Email(message = "Le format de l'email est invalide")
        String email,

        @Schema(description = "Mot de passe du client", example = "345Jaqen@")
        @NotBlank(message = "Le mot de passe est obligatoire")
        @Size(min = 8, max = 16, message = "Le mot de passe doit être entre 8 et 16 caractères")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[&\\#@\\-_%§]).{6,}$",
                message = "Le mot de passe doit contenir au moins une lettre majuscule, une lettre minuscule, et un des symboles suivants : & # @ - _ §")
        String password,

        @Schema(description = "Adresse du client")
        @NotNull(message = "L'adresse est obligatoire")
        AdresseDto adresse,

        @Schema(description = "Date de naissance du client", example = "2000-05-01")
        @NotNull(message = "La date de naissance est obligatoire")
        @Past(message = "La date de naissance doit être dans le passé")
        LocalDate dateNaissance,

        @Schema(description = "Liste des permis du client", example = " [\"A\", \"B\"]")
        @NotNull(message = "La liste des permis est obligatoire")
        List<Permis> listePermis

) {
}

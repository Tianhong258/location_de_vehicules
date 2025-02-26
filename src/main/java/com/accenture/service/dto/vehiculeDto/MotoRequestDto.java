package com.accenture.service.dto.vehiculeDto;

import com.accenture.model.Transmission;
import com.accenture.model.TypeMoto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MotoRequestDto(

        @NotBlank(message = "La marque est obligatoire")
        String marque,
        @NotBlank(message = "Le modèle est obligatoire")
        String modele,
        @NotBlank(message = "La couleur est obligatoire")
        String couleur,
        @NotNull
        @Positive(message = "Le nombre de cylindres doit être un entier positif")
        Integer nombreCylindres,
        @NotNull
        @Positive(message = "La cylindrée doit être un entier positif")
        Integer cylindree,
        @NotNull
        @Positive(message = "Le poids doit être un entier positif")
        Integer poids,
        @NotNull
        @Positive(message = "La poussance doit être un nombre positif")
        Double puissance,
        @NotNull
        @Positive(message = "La hauteure de selle doit être un nombre positif")
        Double hauteurSelle,
        @NotNull
        Transmission transmission,
        @NotNull
        TypeMoto type,
        @NotNull
        @Positive(message = "La hauteur de selle doit être un nombre positif")
        Double tarifParJour,
        @NotNull
        @Positive(message = "Le kilomètrage doit être un entier positif")
        Integer kilometrage,
        @NotNull
        Boolean actif,
        @NotNull
        Boolean retire
) {
}

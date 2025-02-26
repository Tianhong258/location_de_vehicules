package com.accenture.service.dto.vehiculeDto;

import com.accenture.model.NombrePortesVoiture;
import com.accenture.model.Transmission;
import com.accenture.model.TypeCarburant;
import com.accenture.model.TypeVoiture;
import jakarta.validation.constraints.*;


public record VoitureRequestDto(
        @NotBlank(message = "La marque est obligatoire")
        String marque,
        @NotBlank(message = "Le modèle est obligatoire")
        String modele,
        @NotBlank(message = "La couleur est obligatoire")
        String couleur,
        @NotNull
        @Min(value = 1, message = "Le nombre de places doit être un entier positif")
        @Max(value = 16, message = "Le nombre de places doit être un entier inférieur à 17")
        Integer nombrePlaces,
        @NotNull(message = "Le type de carburant est obligatoire")
        TypeCarburant typeCarburant,
        @NotNull(message = "Le nombre de portes est obligatoire")
        NombrePortesVoiture nombrePortes,
        @NotNull(message = "La transmission de carburant est obligatoire")
        Transmission transmission,
        @NotNull(message = "La climatisation est obligatoire")
        Boolean climatisation,
        @NotNull
        @Positive(message = "Le nombre de bagages doit être un entier positif")
        Integer nombreBagages,
        @NotNull(message = "Le type de voiture est obligatoire")
        TypeVoiture type,
        @NotNull
        @Positive(message = "Le tarif par jour doit être un double positif")
        Double tarifParJour,
        @NotNull
        @Positive(message = "Le kilometrage doit être un entier positif")
        Integer kilometrage,
        @NotNull(message = "L'actif est obligatoire")
        Boolean actif,
        @NotNull(message = "Le retire est obligatoire")
        Boolean retire
) {


}

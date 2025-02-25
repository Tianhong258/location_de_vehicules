package com.accenture.service.dto.vehiculeDto;

import com.accenture.model.Transmission;
import com.accenture.model.TypeCarburant;
import com.accenture.model.TypeVoiture;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record VoitureRequestDto(
        @NotBlank(message = "La marque est obligatoire")
        String marque,
        @NotBlank(message = "Le modèle est obligatoire")
        String modele,
        @NotBlank(message = "La couleur est obligatoire")
        String couleur,
        @Positive(message = "Le nombre de places doit être un entier positif")
        Integer nombrePlaces,
        @NotNull(message = "Le type de carburant est obligatoire")
        TypeCarburant typeCarburant,
        @Positive(message = "Le nombre de portes doit être un entier positif")
        Integer nombrePortes,
        @NotNull(message = "La transmission de carburant est obligatoire")
        Transmission transmission,
        @NotNull(message = "La climatisation est obligatoire")
        Boolean climatisation,
        @Positive(message = "Le nombre de bagages doit être un entier positif")
        Integer nombreBagages,
        @NotNull(message = "Le type de voiture est obligatoire")
        TypeVoiture type,
        @Positive(message = "Le tarif par jour doit être un double positif")
        Double tarifParJour,
        @Positive(message = "Le kilometrage doit être un entier positif")
        Integer kilometrage,
        @NotNull(message = "L'actif est obligatoire")
        Boolean actif,
        @NotNull(message = "Le retire est obligatoire")
        Boolean retire
) {


}

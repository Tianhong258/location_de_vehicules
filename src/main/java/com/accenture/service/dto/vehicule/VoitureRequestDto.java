package com.accenture.service.dto.vehicule;

import com.accenture.model.NombrePortesVoiture;
import com.accenture.model.Transmission;
import com.accenture.model.TypeCarburant;
import com.accenture.model.TypeVoiture;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "Détails de la demande de voiture")
public record VoitureRequestDto(

        @Schema(description = "Marque de la voiture", example = "Wouf Wouf")
        @NotBlank(message = "La marque est obligatoire")
        String marque,


        @Schema(description = "Modèle de la voiture", example = "Parfait pour acheter des croquettes")
        @NotBlank(message = "Le modèle est obligatoire")
        String modele,

        @Schema(description = "Couleur de la voiture", example = "Marron comme des croquettes")
        @NotBlank(message = "La couleur est obligatoire")
        String couleur,

        @Schema(description = "Nombre de places de la voiture", example = "6")
        @NotNull(message = "Le nombre de places est obligatoire")
        @Min(value = 1, message = "Le nombre de places doit être un entier positif")
        @Max(value = 16, message = "Le nombre de places doit être un entier inférieur à 17")
        Integer nombrePlaces,


        @Schema(description = "Type de carburant de la voiture", example = "ESSENCE")
        @NotNull(message = "Le type de carburant est obligatoire")
        TypeCarburant typeCarburant,

        @Schema(description = "Nombre de portes de la voiture", example = "CINQ")
        @NotNull(message = "Le nombre de portes est obligatoire")
        NombrePortesVoiture nombrePortes,


        @Schema(description = "Transmission de la voiture", example = "AUTO")
        @NotNull(message = "La transmission de carburant est obligatoire")
        Transmission transmission,

        @Schema(description = "Climatisation de la voiture", example = "true")
        @NotNull(message = "La climatisation est obligatoire")
        Boolean climatisation,


        @Schema(description = "Nombre de bagages que la voiture peut transporter", example = "3")
        @NotNull(message = "Le nombre de bagages est obligatoire")
        @Positive(message = "Le nombre de bagages doit être un entier positif")
        Integer nombreBagages,

        @Schema(description = "Type de la voiture", example = "SUV")
        @NotNull(message = "Le type de voiture est obligatoire")
        TypeVoiture type,

        @Schema(description = "Tarif par jour de location de la voiture", example = "59.99")
        @NotNull(message = "Le tarif par jour est obligatoire")
        @Positive(message = "Le tarif par jour doit être un double positif")
        Double tarif,


        @Schema(description = "Kilométrage de la voiture", example = "15000")
        @NotNull(message = "Le kilométrage est obligatoire")
        @Positive(message = "Le kilometrage doit être un entier positif")
        Integer kilometrage,

        @Schema(description = "Indique si la voiture est active", example = "true")
        @NotNull(message = "L'actif est obligatoire")
        Boolean actif,

        @Schema(description = "Indique si la voiture est retirée", example = "false")
        @NotNull(message = "Le retire est obligatoire")
        Boolean retire
) {


}

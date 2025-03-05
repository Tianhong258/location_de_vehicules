package com.accenture.service.dto.vehicule;

import com.accenture.model.Transmission;
import com.accenture.model.TypeMoto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


@Schema(description = "Détails de la demande de moto")
public record MotoRequestDto(
        @Schema(description = "Marque de la moto", example = "Wong Wong")
        @NotBlank(message = "La marque est obligatoire")
        String marque,

        @Schema(description = "Modèle de la moto", example = "Modèle parfait pour aller à la plage")
        @NotBlank(message = "Le modèle est obligatoire")
        String modele,

        @Schema(description = "Couleur de la moto", example = "Bleu comme la mer")
        @NotBlank(message = "La couleur est obligatoire")
        String couleur,

        @Schema(description = "Nombre de cylindres de la moto", example = "3")
        @NotNull(message = "Le nombre de cylindres est obligatoire")
        @Positive(message = "Le nombre de cylindres doit être un entier positif")
        Integer nombreCylindres,

        @Schema(description = "Cylindrée de la moto", example = "5")
        @NotNull(message = "La cylindrée est obligatoire")
        @Positive(message = "La cylindrée doit être un entier positif")
        Integer cylindree,

        @Schema(description = "Poids de la moto", example = "100")
        @NotNull(message = "Le poids est obligatoire")
        @Positive(message = "Le poids doit être un entier positif")
        Integer poids,

        @Schema(description = "Puissance de la moto", example = "35.6")
        @NotNull(message = "La puissance est obligatoire")
        @Positive(message = "La puissance doit être un nombre positif")
        Double puissance,

        @Schema(description = "Hauteur de selle de la moto", example = "3.75")
        @NotNull(message = "La hauteur de selle est obligatoire")
        @Positive(message = "La hauteur de selle doit être un nombre positif")
        Double hauteurSelle,

        @Schema(description = "Transmission de la moto", example = "AUTO")
        @NotNull(message = "La transmission est obligatoire")
        Transmission transmission,

        @Schema(description = "Type de la moto", example = "SPORTIVE")
        @NotNull(message = "Le type est obligatoire")
        TypeMoto type,

        @Schema(description = "Tarif par jour de la moto", example = "68.3")
        @NotNull(message = "Le tarif est obligatoire")
        @Positive(message = "Le tarif doit être un nombre positif")
        Double tarif,

        @Schema(description = "Kilomètrage de la moto", example = "329.3")
        @NotNull(message = "Le kilomètrage est obligatoire")
        @Positive(message = "Le kilomètrage doit être un entier positif")
        Integer kilometrage,

        @Schema(description = "Indique si la moto est active", example = "true")
        @NotNull(message = "L'actif est obligatoire")
        Boolean actif,

        @Schema(description = "Indique si la moto est retirée", example = "false")
        @NotNull(message = "Le retire est obligatoire")
        Boolean retire
) {
}

package com.accenture.service.dto.location;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

@Schema(description = "Détails de la demande de location")
public record LocationRequestDto(

        @Schema(description = "Id du véhicule", example = "1")
        @NotNull(message = "L'id du véhicule est obligatoire")
        @Positive(message = "Id de véhicule doit être un nombre positif")
        long vehiculeId,

        //Accessoire accessoire,

        @Schema(description = "Date de début de location", example = "2025-04-12")
        @NotNull(message = "La date de début de location est obligatoire")
        @FutureOrPresent(message = "La date de début de location doit être aujourd'hui ou après")
        LocalDate debut,

        @Schema(description = "Date de fin de location", example = "2025-04-23")
        @NotNull(message = "La date de fin de location est obligatoire")
        @FutureOrPresent(message = "La date de fin de location doit être aujourd'hui ou après")
        LocalDate fin,

        @Schema(description = "Kilometres parcours pendant la location", example = "534")
        @NotNull(message = "Kilometres parcours est obligatoire")
        @Positive(message = "Kilometre doit être un entier positif")
        Integer kilometrage


) {
}

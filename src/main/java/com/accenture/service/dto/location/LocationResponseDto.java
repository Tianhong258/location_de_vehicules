package com.accenture.service.dto.location;

import com.accenture.model.Etat;

import java.time.LocalDate;

public record LocationResponseDto(
        long id,
        Integer clientId,
        String email,
        Integer vehiculeId,
        //Accessoire accessoire,
        LocalDate debut,
        LocalDate fin,
        Integer kilometrage,
        Double montant,
        LocalDate dateValidation,
        Etat etat
) {
}

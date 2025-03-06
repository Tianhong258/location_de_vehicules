package com.accenture.service.dto.location;

import com.accenture.model.Etat;
import com.accenture.repository.entity.vehicule.Vehicule;
import com.accenture.service.dto.utilisateur.ClientResponseDto;

import java.time.LocalDate;

public record LocationResponseDto(
        long id,
        ClientResponseDto client,
        Vehicule vehicule,
        //Accessoire accessoire,
        LocalDate debut,
        LocalDate fin,
        Integer kilometrage,
        Double montant,
        LocalDate dateValidation,
        Etat etat
) {
}

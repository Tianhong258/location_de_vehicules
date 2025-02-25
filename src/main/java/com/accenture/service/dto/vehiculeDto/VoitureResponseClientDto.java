package com.accenture.service.dto.vehiculeDto;

import com.accenture.model.Permis;
import com.accenture.model.Transmission;
import com.accenture.model.TypeCarburant;
import com.accenture.model.TypeVoiture;

public record VoitureResponseClientDto(
        long id,
        String marque,
        String modele,
        String couleur,
        TypeCarburant typeCarburant,
        Integer nombrePortes,
        Transmission transmission,
        Boolean climatisation,
        Integer nombreBagages,
        TypeVoiture type,
        Permis permis
) {
}

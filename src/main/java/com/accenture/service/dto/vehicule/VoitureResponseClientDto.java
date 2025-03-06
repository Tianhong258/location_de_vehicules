package com.accenture.service.dto.vehicule;

import com.accenture.model.*;

import java.util.List;

public record VoitureResponseClientDto(
        long id,
        String marque,
        String modele,
        String couleur,
        Integer nombrePlaces,
        TypeCarburant typeCarburant,
        NombrePortesVoiture nombrePortes,
        Transmission transmission,
        Boolean climatisation,
        Integer nombreBagages,
        TypeVoiture typeVoiture,
        Permis permis,
        Double tarif
) {
}

package com.accenture.service.dto.vehicule;

import com.accenture.model.*;

import java.util.List;

public record VoitureResponseAdminDto(
        long id,
        String marque,
        String modele,
        String couleur,
        TypeCarburant typeCarburant,
        Integer nombrePlaces,
        NombrePortesVoiture nombrePortes,
        Transmission transmission,
        Boolean climatisation,
        Integer nombreBagages,
        TypeVoiture type,
        List<Permis> permis,
        Double tarif,
        Integer kilometrage,
        Boolean actif,
        Boolean retire
) {
}

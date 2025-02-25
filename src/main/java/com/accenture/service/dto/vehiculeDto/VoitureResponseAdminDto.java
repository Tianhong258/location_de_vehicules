package com.accenture.service.dto.vehiculeDto;

import com.accenture.model.Permis;
import com.accenture.model.Transmission;
import com.accenture.model.TypeCarburant;
import com.accenture.model.TypeVoiture;

public record VoitureResponseAdminDto(
        long id,
        String marque,
        String modele,
        String couleur,
        TypeCarburant typeCarburant,
        int nombrePortes,
        Transmission transmission,
        boolean climatisation,
        int nombreBagages,
        TypeVoiture type,
        Permis permis,
        double tarifParJour,
        int kilometrage,
        boolean actif,
        boolean retire
) {
}

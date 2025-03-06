package com.accenture.service.dto.vehicule;

import com.accenture.model.Permis;
import com.accenture.model.Transmission;
import com.accenture.model.TypeMoto;

import java.util.List;

public record MotoResponseAdminDto(
        long id,
        String marque,
        String modele,
        String couleur,
        Integer nombreCylindres,
        Integer cylindree,
        Integer poids,
        Double puissance,
        Double hauteurSelle,
        Transmission transmission,
        TypeMoto typeMoto,
        Permis permis,
        Double tarif,
        Integer kilometrage,
        Boolean actif,
        Boolean retire
) {
}

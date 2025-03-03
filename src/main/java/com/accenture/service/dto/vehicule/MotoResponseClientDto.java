package com.accenture.service.dto.vehicule;

import com.accenture.model.Permis;
import com.accenture.model.Transmission;
import com.accenture.model.TypeMoto;

import java.util.List;

public record MotoResponseClientDto(
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
        TypeMoto type,
        List<Permis> permis,
        Double tarif

) {
}

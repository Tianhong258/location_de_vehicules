package com.accenture.service.dto.utilisateurDto;

import com.accenture.model.Permis;

import java.time.LocalDate;
import java.util.List;

public record ClientResponseDto (
        long id,
        String nom,
        String prenom,
        String email,
        AdresseDto adresse,
        LocalDate dateNaissance,
        LocalDate dateInscription,
        List<Permis> listePermis
){
}

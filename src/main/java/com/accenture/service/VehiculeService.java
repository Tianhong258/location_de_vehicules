package com.accenture.service;



import com.accenture.exception.UtilisateurException;
import com.accenture.model.CategorieVehicule;
import com.accenture.model.Filtre;
import com.accenture.service.dto.vehicule.VehiculeAdminDto;
import com.accenture.service.dto.vehicule.VehiculeClientDto;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDate;


public interface VehiculeService {

    //VehiculeAdminDto trouverTous();
    VehiculeAdminDto filtrer(Filtre filtre);
    VehiculeClientDto rechercherParDate(LocalDate debut, LocalDate fin, String password, String email, CategorieVehicule categorie) throws UtilisateurException, EntityNotFoundException;
}

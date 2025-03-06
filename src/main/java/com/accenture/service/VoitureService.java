package com.accenture.service;

import com.accenture.exception.VehiculeException;
import com.accenture.model.Filtre;
import com.accenture.service.dto.vehicule.VoitureRequestDto;
import com.accenture.service.dto.vehicule.VoitureResponseAdminDto;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface VoitureService {
    VoitureResponseAdminDto ajouter(VoitureRequestDto voitureRequestDto) throws VehiculeException;
    List<VoitureResponseAdminDto> trouverToutes();
    VoitureResponseAdminDto trouver(long id) throws EntityNotFoundException;
    List<VoitureResponseAdminDto> filtrer(Filtre filtre);
    void supprimerOuRetire(long id) throws EntityNotFoundException;
    VoitureResponseAdminDto modifier(long id, VoitureRequestDto voitureRequestDto) throws VehiculeException, EntityNotFoundException;
}

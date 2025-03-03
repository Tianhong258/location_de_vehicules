package com.accenture.service;

import com.accenture.exception.VehiculeException;
import com.accenture.model.Filtre;
import com.accenture.service.dto.vehicule.MotoRequestDto;
import com.accenture.service.dto.vehicule.MotoResponseAdminDto;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface MotoService {
    MotoResponseAdminDto ajouter(MotoRequestDto motoRequestDto) throws VehiculeException;
    List<MotoResponseAdminDto> trouverToutes();
    MotoResponseAdminDto trouver(long id) throws EntityNotFoundException;
    List<MotoResponseAdminDto> filtrer(Filtre filtre);
    void supprimer(long id) throws EntityNotFoundException;
    MotoResponseAdminDto modifier(long id, MotoRequestDto motoRequestDto) throws VehiculeException, EntityNotFoundException;
}


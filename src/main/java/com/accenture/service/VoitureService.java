package com.accenture.service;

import com.accenture.exception.VoitureException;
import com.accenture.model.Filtre;
import com.accenture.service.dto.vehiculeDto.VoitureRequestDto;
import com.accenture.service.dto.vehiculeDto.VoitureResponseAdminDto;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface VoitureService {
    VoitureResponseAdminDto ajouter(VoitureRequestDto voitureRequestDto) throws VoitureException;
    List<VoitureResponseAdminDto> trouverToutes();
    VoitureResponseAdminDto trouver(long id) throws EntityNotFoundException;
    List<VoitureResponseAdminDto> filtrer(Filtre filtre);
    void supprimer(long id) throws EntityNotFoundException;
    VoitureResponseAdminDto modifier(long id, VoitureRequestDto voitureRequestDto) throws VoitureException, EntityNotFoundException;
}

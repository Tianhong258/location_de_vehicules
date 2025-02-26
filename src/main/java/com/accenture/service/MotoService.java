package com.accenture.service;

import com.accenture.exception.MotoException;
import com.accenture.model.Filtre;
import com.accenture.service.dto.vehiculeDto.MotoRequestDto;
import com.accenture.service.dto.vehiculeDto.MotoResponseAdminDto;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface MotoService {
    MotoResponseAdminDto ajouter(MotoRequestDto motoRequestDto) throws MotoException;
    List<MotoResponseAdminDto> trouverToutes();
    MotoResponseAdminDto trouver(long id) throws EntityNotFoundException;
    List<MotoResponseAdminDto> filtrer(Filtre filtre);
    void supprimer(long id) throws EntityNotFoundException;
    MotoResponseAdminDto modifier(long id, MotoRequestDto motoRequestDto) throws MotoException, EntityNotFoundException;
}


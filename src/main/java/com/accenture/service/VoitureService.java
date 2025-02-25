package com.accenture.service;

import com.accenture.exception.VoitureException;
import com.accenture.service.dto.vehiculeDto.VoitureRequestDto;
import com.accenture.service.dto.vehiculeDto.VoitureResponseAdminDto;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface VoitureService {
    VoitureResponseAdminDto ajouter(VoitureRequestDto voitureRequestDto) throws VoitureException;
    List<VoitureResponseAdminDto> trouverToutes();
    VoitureResponseAdminDto trouver(long id) throws EntityNotFoundException;
}

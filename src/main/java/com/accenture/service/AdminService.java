package com.accenture.service;

import com.accenture.exception.UtilisateurException;
import com.accenture.service.dto.utilisateur.AdminRequestDto;
import com.accenture.service.dto.utilisateur.AdminResponseDto;
import jakarta.persistence.EntityNotFoundException;

import java.security.Principal;
import java.util.List;

public interface AdminService {
    AdminResponseDto ajouter(AdminRequestDto adminRequestDto) throws UtilisateurException;

    AdminResponseDto trouver(Principal principal);

    List<AdminResponseDto> trouverTous();

    void supprimer(Principal principal) throws UtilisateurException;

    AdminResponseDto modifierPartiellement(Principal principal, AdminRequestDto adminRequestDto) throws UtilisateurException;

}

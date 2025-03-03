package com.accenture.service;

import com.accenture.exception.UtilisateurException;
import com.accenture.service.dto.utilisateur.AdminRequestDto;
import com.accenture.service.dto.utilisateur.AdminResponseDto;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface AdminService {
    AdminResponseDto ajouter(AdminRequestDto adminRequestDto) throws UtilisateurException;

    AdminResponseDto trouver(String email, String password) throws UtilisateurException, EntityNotFoundException;

    List<AdminResponseDto> trouverTous();

    void supprimer(String email, String password) throws EntityNotFoundException, UtilisateurException;

    AdminResponseDto modifier(String email, String password, AdminRequestDto adminRequestDto) throws EntityNotFoundException, UtilisateurException;

    AdminResponseDto modifierPartiellement(String email, String password, AdminRequestDto adminRequestDto) throws UtilisateurException, EntityNotFoundException;

}

package com.accenture.service;

import com.accenture.exception.UtilisateurException;
import com.accenture.service.dto.utilisateur.ClientRequestDto;
import com.accenture.service.dto.utilisateur.ClientResponseDto;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface ClientService {
    ClientResponseDto ajouter(ClientRequestDto clientRequestDto) throws UtilisateurException;
    ClientResponseDto trouver(String email, String password) throws EntityNotFoundException;
    List<ClientResponseDto> trouverTous();
    void desactiverOuSupprimer(String email, String password) throws UtilisateurException, EntityNotFoundException;
    ClientResponseDto modifier(String email, String password, ClientRequestDto clientRequestDto) throws UtilisateurException, EntityNotFoundException;
    ClientResponseDto modifierPartiellement(String email, String password, ClientRequestDto clientRequestDto) throws UtilisateurException, EntityNotFoundException;

}

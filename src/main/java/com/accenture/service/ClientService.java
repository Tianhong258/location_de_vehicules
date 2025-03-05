package com.accenture.service;

import com.accenture.exception.UtilisateurException;
import com.accenture.service.dto.utilisateur.ClientRequestDto;
import com.accenture.service.dto.utilisateur.ClientResponseDto;
import jakarta.persistence.EntityNotFoundException;

import java.security.Principal;
import java.util.List;

public interface ClientService {
    ClientResponseDto ajouter(ClientRequestDto clientRequestDto) throws UtilisateurException;
    ClientResponseDto trouver(Principal principal);
    List<ClientResponseDto> trouverTous();
    void desactiverOuSupprimer(Principal principal) throws UtilisateurException;
    ClientResponseDto modifierPartiellement(Principal principal, ClientRequestDto clientRequestDto) throws UtilisateurException;

}

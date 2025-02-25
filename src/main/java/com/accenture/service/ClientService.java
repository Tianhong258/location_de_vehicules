package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.repository.entity.utilisateur.Client;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface ClientService {
    ClientResponseDto ajouter(ClientRequestDto clientRequestDto) throws ClientException;
    ClientResponseDto trouver(String email, String password) throws EntityNotFoundException;
    List<ClientResponseDto> trouverTous();
    void desactiverOuSupprimer(String email, String password) throws ClientException, EntityNotFoundException;
    ClientResponseDto modifier(String email, String password, ClientRequestDto clientRequestDto) throws ClientException, EntityNotFoundException;
    ClientResponseDto modifierPartiellement(String email, String password, ClientRequestDto clientRequestDto) throws ClientException, EntityNotFoundException;

}

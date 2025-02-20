package com.accenture.service;

import com.accenture.repository.entity.utilisateur.Client;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;

import java.util.List;

public interface ClientService {
    ClientResponseDto ajouter(ClientRequestDto clientRequestDto);
    //List<ClientResponseDto> liste();


}

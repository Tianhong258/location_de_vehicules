package com.accenture.service.mapper;

import com.accenture.repository.entity.utilisateur.Client;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = AdresseMapper.class)
public interface ClientMapper {

    Client toClient(ClientRequestDto clientRequestDto);
    ClientResponseDto toClientResponseDto (Client client);
}

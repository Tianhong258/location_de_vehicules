package com.accenture.service.mapper.utilisateurMapper;

import com.accenture.repository.entity.utilisateur.Client;
import com.accenture.service.dto.utilisateurDto.ClientRequestDto;
import com.accenture.service.dto.utilisateurDto.ClientResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = AdresseMapper.class)
public interface ClientMapper {

    Client toClient(ClientRequestDto clientRequestDto);
    ClientResponseDto toClientResponseDto (Client client);
}

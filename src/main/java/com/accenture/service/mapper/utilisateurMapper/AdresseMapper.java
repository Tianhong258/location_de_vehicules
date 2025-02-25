package com.accenture.service.mapper.utilisateurMapper;

import com.accenture.repository.entity.utilisateur.Adresse;
import com.accenture.service.dto.utilisateurDto.AdresseDto;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdresseMapper {
    Adresse toAdresse(AdresseDto adresseDto);
    AdresseDto toAdresseResponseDto (Adresse adresse);
}

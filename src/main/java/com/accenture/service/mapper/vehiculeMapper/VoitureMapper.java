package com.accenture.service.mapper.vehiculeMapper;

import com.accenture.repository.entity.vehicule.Voiture;
import com.accenture.service.dto.vehiculeDto.VoitureRequestDto;
import com.accenture.service.dto.vehiculeDto.VoitureResponseAdminDto;
import com.accenture.service.dto.vehiculeDto.VoitureResponseClientDto;
import com.accenture.service.mapper.utilisateurMapper.AdresseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VoitureMapper {
    Voiture toVoiture(VoitureRequestDto voitureRequestDto);
    VoitureResponseAdminDto toVoitureResponseAdminDto (Voiture voiture);
    VoitureResponseClientDto toVoitureResponseClientDto (Voiture voiture);
}

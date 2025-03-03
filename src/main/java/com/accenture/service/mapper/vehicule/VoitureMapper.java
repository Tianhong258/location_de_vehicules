package com.accenture.service.mapper.vehicule;

import com.accenture.repository.entity.vehicule.Voiture;
import com.accenture.service.dto.vehicule.VoitureRequestDto;
import com.accenture.service.dto.vehicule.VoitureResponseAdminDto;
import com.accenture.service.dto.vehicule.VoitureResponseClientDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VoitureMapper {
    Voiture toVoiture(VoitureRequestDto voitureRequestDto);
    VoitureResponseAdminDto toVoitureResponseAdminDto (Voiture voiture);
    VoitureResponseClientDto toVoitureResponseClientDto (Voiture voiture);
}

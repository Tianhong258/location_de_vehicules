package com.accenture.service.mapper.vehicule;

import com.accenture.repository.entity.vehicule.Moto;
import com.accenture.service.dto.vehicule.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MotoMapper {
    Moto toMoto(MotoRequestDto motoRequestDto);
    MotoResponseAdminDto toMotoResponseAdminDto (Moto moto);
    MotoResponseClientDto toMotoResponseClientDto (Moto moto);
}

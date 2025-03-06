package com.accenture.service.mapper.location;

import com.accenture.repository.entity.location.Location;
import com.accenture.service.dto.location.LocationRequestDto;
import com.accenture.service.dto.location.LocationResponseDto;
import com.accenture.service.mapper.utilisateur.ClientMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",  uses = {ClientMapper.class})
public interface LocationMapper {
    Location toLocation(LocationRequestDto locationRequestDto);
    LocationResponseDto toLocationResponseDto(Location location);
}

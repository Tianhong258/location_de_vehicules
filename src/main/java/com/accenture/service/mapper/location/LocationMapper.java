package com.accenture.service.mapper.location;

import com.accenture.repository.entity.location.Location;
import com.accenture.service.dto.location.LocationRequestDto;
import com.accenture.service.dto.location.LocationResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    Location toLocation(LocationRequestDto locationRequestDto);
    LocationResponseDto toLocationResponseDto(Location location);
}

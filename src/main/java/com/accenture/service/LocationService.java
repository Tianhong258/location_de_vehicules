package com.accenture.service;

import com.accenture.exception.LocationException;
import com.accenture.service.dto.location.LocationRequestDto;
import com.accenture.service.dto.location.LocationResponseDto;

import java.security.Principal;
import java.util.List;

public interface LocationService {
    List<LocationResponseDto> locations();
    LocationResponseDto ajouter(Principal principal, LocationRequestDto locationRequestDto) throws LocationException;
}

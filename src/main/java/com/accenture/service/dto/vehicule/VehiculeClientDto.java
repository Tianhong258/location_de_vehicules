package com.accenture.service.dto.vehicule;

import java.util.List;

public record VehiculeClientDto(
        List<VoitureResponseClientDto> voitures,
        List<MotoResponseClientDto> motos
) {
}

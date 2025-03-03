package com.accenture.service.dto.vehicule;
import java.util.List;

public record VehiculeAdminDto(
        List<VoitureResponseAdminDto> voitures,
        List<MotoResponseAdminDto> motos
) {
}

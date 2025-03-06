package com.accenture.controller.location;

import com.accenture.service.LocationService;
import com.accenture.service.dto.location.LocationRequestDto;
import com.accenture.service.dto.location.LocationResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/locations")
@Tag(name = "Locations", description = "Gestion des locations")
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    /**
     * <p>La méthode <code>ajouter</code> permet d'ajouter une nouvelle location.</p>
     *
     * @param locationRequestDto Les informations de la location à ajouter.
     * @return Une réponse HTTP avec un statut HTTP CREATED (201) et l'URI de la ressource créée.
     */
    @PostMapping
    @Operation(summary = "Ajouter une nouvelle location", description ="Ajoute une nouvelle location à la base de données")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Location créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    ResponseEntity<Void> ajouter(Principal principal, @RequestBody @Valid LocationRequestDto locationRequestDto) {
        log.info("L'ajoute de location commence avec l'email de client/admin : {}", principal.getName());
        LocationResponseDto locationEnreg = locationService.ajouter(principal, locationRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(locationEnreg.id())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    /**
     * <p>La méthode <code>locations</code> permet de récupérer la liste de toutes les locations.</p>
     *
     * @return Une liste d'objets <code>LocationResponseDto</code> représentant les locations.
     */
    @GetMapping
    @Operation(summary = "Récupérer toutes les locations", description ="Récupération de toutes les locations depuis la base de données")
    @ApiResponse(responseCode = "200", description = "Récupération effectuée avec succès")
    List<LocationResponseDto> trouverToutes(){
        log.info("La récupération de toute les locations commence");
        return locationService.trouverToutes();
    }

}

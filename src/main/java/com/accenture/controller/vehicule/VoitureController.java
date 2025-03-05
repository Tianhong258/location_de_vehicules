package com.accenture.controller.vehicule;

import com.accenture.model.Filtre;
import com.accenture.service.VoitureService;
import com.accenture.service.dto.vehicule.VoitureRequestDto;
import com.accenture.service.dto.vehicule.VoitureResponseAdminDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/voitures")
@Tag(name = "Voitures", description = "Gestion des voitures")
public class VoitureController {
    private final VoitureService voitureService;

    public VoitureController(VoitureService voitureService) {
        this.voitureService = voitureService;
    }
    /**
     * <p>La méthode <code>ajouter</code> permet de créer une nouvelle voiture.</p>
     *
     * @param voitureRequestDto Les informations de la voiture à ajouter.
     * @return Une réponse HTTP avec le statut HTTP Created (201) et l'URI de la voiture créée.
     */
    @PostMapping
    @Operation(summary = "Ajouter une nouvelle voiture", description ="Ajoute une nouvelle voiture à la base de données")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Voiture créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    ResponseEntity<Void> ajouter(@RequestBody @Valid VoitureRequestDto voitureRequestDto){
        log.info("L'ajoute d'une voiture commence avec sa marque : {}", voitureRequestDto.marque());
        VoitureResponseAdminDto voitureEnreg = voitureService.ajouter(voitureRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(voitureEnreg.id())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    /**
     * <p>La méthode <code>trouverToutes</code> permet de récupérer toutes les voitures.</p>
     *
     * @return Une liste de toutes les voitures enregistrées.
     */
    @GetMapping
    @Operation(summary = "Récupérer toutes les voitures", description ="Récupération de toutes les voitures depuis la base de données")
    @ApiResponse(responseCode = "200", description = "Récupération effectuée avec succès")
    List<VoitureResponseAdminDto> trouverToutes(){
        log.info("La récupération de toutes voitures commence");
        return voitureService.trouverToutes();
    }


    /**
     * <p>La méthode <code>trouver</code> permet de récupérer une voiture par son id.</p>
     *
     * @param id L'id de la voiture à récupérer.
     * @return Une réponse HTTP avec le statut HTTP OK (200) et les informations de la voiture recherchée.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer les informations d'une voiture", description = "Récupération des informations d'une voiture depuis la base de données")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Récupération effectuée avec succès"),
            @ApiResponse(responseCode = "404", description = "Récupération échouée")
    })
    ResponseEntity<VoitureResponseAdminDto> trouver(
           @PathVariable("id") long id
    ){
        log.info("La récupération les infos d'une voiture commence avec son id : {}", id);
        VoitureResponseAdminDto trouve = voitureService.trouver(id);
        return ResponseEntity.ok(trouve);
    }

    /**
     * <p>La méthode <code>filtrer</code> permet de récupérer des voitures filtrées selon des critères spécifiques.</p>
     *
     * @param filtre Les critères de filtrage des voitures : actif, non actif, retire, non retire.
     * @return Une liste de voitures correspondant aux critères de filtrage.
     */
    @GetMapping("/filtrer")
    @Operation(
            summary = "Récupérer toutes les voitures selon la condition",
            description = "Récupération des voitures selon leur état : actif, non actif, retiré ou non retiré"
    )
    @ApiResponse(responseCode = "200", description = "Récupération effectuée avec succès")
    List<VoitureResponseAdminDto> filtrer (
            @Parameter(description = "Condition de récupération") @RequestParam Filtre filtre
    ) {
        log.info("La récupération les voitures commence avec la condition : {}", filtre);
       return voitureService.filtrer(filtre);
    }


    /**
     * <p>La méthode <code>supprimer</code> permet de supprimer une voiture par son id.</p>
     *
     * @param id L'id de la voiture à supprimer.
     * @return Une réponse HTTP avec le statut HTTP No Content (204).
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une voiture", description = "Suppression d'une voiture depuis la base de données")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Suppression effectuée avec succès"),
            @ApiResponse(responseCode = "404", description = "Suppression échouée")
    })
    ResponseEntity<Void> supprimer(@PathVariable("id") long id){
        log.info("La suppression d'une voiture commence avec son id : {}", id);
        voitureService.supprimer(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * <p>La méthode <code>modifier</code> permet de modifier partiellement les informations d'une voiture existante.</p>
     *
     * @param id L'id de la voiture à modifier.
     * @param voitureRequestDto Les nouvelles informations de la voiture.
     * @return Une réponse HTTP avec le statut HTTP OK (200) et les informations mises à jour de la voiture.
     */

    @PatchMapping("/{id}")
    @Operation(summary = "Modifier une voiture", description = "Modification d'une voiture dans la base de données")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Modification effectuée avec succès"),
            @ApiResponse(responseCode = "404", description = "Modification échouée"),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    ResponseEntity<VoitureResponseAdminDto> modifier(
            @PathVariable("id") long id,
            @RequestBody VoitureRequestDto voitureRequestDto
    ){
        log.info("La modification les infos d'une voiture commence avec son id : {}", id);
        VoitureResponseAdminDto reponse = voitureService.modifier(id, voitureRequestDto);
        return ResponseEntity.ok(reponse);
    }



}

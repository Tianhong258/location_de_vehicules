package com.accenture.controller.vehicule;

import com.accenture.model.Filtre;
import com.accenture.service.MotoService;
import com.accenture.service.dto.vehicule.MotoRequestDto;
import com.accenture.service.dto.vehicule.MotoResponseAdminDto;
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
@RequestMapping("/motos")
@Tag(name = "Motos", description = "Gestion des motos")
public class MotoController {
    private final MotoService motoService;

    public MotoController(MotoService motoService) {
        this.motoService = motoService;
    }

    /**
     * <p>La méthode <code>ajouter</code> permet d'ajouter une nouvelle moto.</p>
     *
     * @param motoRequestDto Les informations de la moto à ajouter.
     * @return Une réponse HTTP avec un statut HTTP CREATED (201) et l'URI de la ressource créée.
     */

    @PostMapping
    @Operation(summary = "Ajouter une nouvelle moto", description = "Ajoute une nouvelle moto à la base de données")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Moto créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    ResponseEntity<Void> ajouter(@RequestBody @Valid MotoRequestDto motoRequestDto) {
        log.info("L'ajoute de la moto commence avec sa marque : {}", motoRequestDto.marque());
        MotoResponseAdminDto motoEnreg = motoService.ajouter(motoRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(motoEnreg.id())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    /**
     * <p>La méthode <code>trouverToutes</code> permet de récupérer toutes les motos.</p>
     *
     * @return Une liste d'objets <code>MotoResponseAdminDto</code> représentant toutes les motos.
     */
    @GetMapping
    @Operation(summary = "Récupérer toutes les motos", description = "Récupération de toutes les motos depuis la base de données")
    @ApiResponse(responseCode = "200", description = "Récupération effectuée avec succès")
    List<MotoResponseAdminDto> trouverToutes() {
        log.info("La récupération de toutes les motos commence");
        return motoService.trouverToutes();
    }


    /**
     * <p>La méthode <code>trouver</code> permet de récupérer une moto en fonction de son id.</p>
     *
     * @param id L'id de la moto à récupérer.
     * @return Une réponse HTTP avec le statut HTTP OK (200) et les informations de la moto trouvée.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer les informations d'une moto", description = "Récupération des informations d'une moto depuis la base de données")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Récupération effectuée avec succès"),
            @ApiResponse(responseCode = "404", description = "Récupération échouée")
    })
    ResponseEntity<MotoResponseAdminDto> trouver(@PathVariable("id") long id) {
        log.info("La récupération de la moto commence avec son id : {}", id);
        MotoResponseAdminDto trouve = motoService.trouver(id);
        return ResponseEntity.ok(trouve);
    }

    /**
     * <p>La méthode <code>filtrer</code> permet de filtrer les motos en fonction des critères spécifiés.</p>
     *
     * @param filtre Les critères de filtrage des motos : actif, non actif, retire, non retire.
     * @return Une liste d'objets <code>MotoResponseAdminDto</code> représentant les motos filtrées.
     */
    @GetMapping("/filtrer")
    @Operation(
            summary = "Récupérer toutes les motos selon la condition",
            description = "Récupération des motos selon leur état : actif, non actif, retiré ou non retiré"
    )
    @ApiResponse(responseCode = "200", description = "Récupération effectuée avec succès")
    List<MotoResponseAdminDto> filtrer(
            @Parameter(description = "Condition de récupération") @RequestParam Filtre filtre
    ) {
        log.info("La récupération des motos commence avec la condition : {}", filtre);
        return motoService.filtrer(filtre);
    }

    /**
     * <p>La méthode <code>supprimer</code> permet de supprimer une moto en fonction de son id.</p>
     *
     * @param id L'id de la moto à supprimer.
     * @return Une réponse HTTP avec le statut HTTP NO_CONTENT (204) si la suppression a réussi.
     */

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une moto", description = "Suppression d'une moto depuis la base de données")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Suppression effectuée avec succès"),
            @ApiResponse(responseCode = "404", description = "Suppression échouée")
    })
    ResponseEntity<Void> supprimer(@PathVariable("id") long id) {
        log.info("La suppression de la moto commence avec son id : {}", id);
        motoService.supprimer(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * <p>La méthode <code>modifier</code> permet de modifier partiellement les informations d'une moto existante.</p>
     *
     * @param id             L'id de la moto à modifier.
     * @param motoRequestDto Les nouvelles informations de la moto.
     * @return Une réponse HTTP avec le statut HTTP OK (200) et les informations mises à jour de la moto.
     */
    @PatchMapping("/{id}")
    @Operation(summary = "Modifier une moto", description = "Modification d'une moto dans la base de données")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Modification effectuée avec succès"),
            @ApiResponse(responseCode = "404", description = "Modification échouée"),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    ResponseEntity<MotoResponseAdminDto> modifier(
            @PathVariable("id") long id,
            @RequestBody MotoRequestDto motoRequestDto
    ) {
        log.info("La modification de la moto commence avec son id : {}", id);
        MotoResponseAdminDto reponse = motoService.modifier(id, motoRequestDto);
        return ResponseEntity.ok(reponse);
    }


}

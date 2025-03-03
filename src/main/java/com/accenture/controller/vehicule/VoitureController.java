package com.accenture.controller.vehicule;

import com.accenture.model.Filtre;
import com.accenture.service.VoitureService;
import com.accenture.service.dto.vehicule.VoitureRequestDto;
import com.accenture.service.dto.vehicule.VoitureResponseAdminDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/voitures")
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
    ResponseEntity<Void> ajouter(@RequestBody @Valid VoitureRequestDto voitureRequestDto){
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
    List<VoitureResponseAdminDto> trouverToutes(){
        return voitureService.trouverToutes();
    }


    /**
     * <p>La méthode <code>trouver</code> permet de récupérer une voiture par son id.</p>
     *
     * @param id L'id de la voiture à récupérer.
     * @return Une réponse HTTP avec le statut HTTP OK (200) et les informations de la voiture recherchée.
     */
    @GetMapping("/{id}")
    ResponseEntity<VoitureResponseAdminDto> trouver(
           @PathVariable("id") long id
    ){
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
    List<VoitureResponseAdminDto> filtrer (@RequestParam Filtre filtre) {
       return voitureService.filtrer(filtre);
    }


    /**
     * <p>La méthode <code>supprimer</code> permet de supprimer une voiture par son id.</p>
     *
     * @param id L'id de la voiture à supprimer.
     * @return Une réponse HTTP avec le statut HTTP No Content (204).
     */
    @DeleteMapping("/{id}")
    ResponseEntity<Void> supprimer(@PathVariable("id") long id){
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
    ResponseEntity<VoitureResponseAdminDto> modifier(
            @PathVariable("id") long id,
            @RequestBody VoitureRequestDto voitureRequestDto
    ){
        VoitureResponseAdminDto reponse = voitureService.modifier(id, voitureRequestDto);
        return ResponseEntity.ok(reponse);
    }



}

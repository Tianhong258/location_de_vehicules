package com.accenture.controller.vehicule;

import com.accenture.model.Filtre;
import com.accenture.service.MotoService;
import com.accenture.service.dto.vehicule.MotoRequestDto;
import com.accenture.service.dto.vehicule.MotoResponseAdminDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/motos")
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
    ResponseEntity<Void> ajouter(@RequestBody @Valid MotoRequestDto motoRequestDto){
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
    List<MotoResponseAdminDto> trouverToutes(){
        return motoService.trouverToutes();
    }


    /**
     * <p>La méthode <code>trouver</code> permet de récupérer une moto en fonction de son id.</p>
     *
     * @param id L'id de la moto à récupérer.
     * @return Une réponse HTTP avec le statut HTTP OK (200) et les informations de la moto trouvée.
     */
    @GetMapping("/{id}")
    ResponseEntity<MotoResponseAdminDto> trouver(
            @PathVariable("id") long id
    ){
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
    List<MotoResponseAdminDto> filtrer (@RequestParam Filtre filtre) {
        return motoService.filtrer(filtre);
    }

    /**
     * <p>La méthode <code>supprimer</code> permet de supprimer une moto en fonction de son id.</p>
     *
     * @param id L'id de la moto à supprimer.
     * @return Une réponse HTTP avec le statut HTTP NO_CONTENT (204) si la suppression a réussi.
     */
    @DeleteMapping("/{id}")
    ResponseEntity<Void> supprimer(@PathVariable("id") long id){
        motoService.supprimer(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * <p>La méthode <code>modifier</code> permet de modifier partiellement les informations d'une moto existante.</p>
     *
     * @param id L'id de la moto à modifier.
     * @param motoRequestDto Les nouvelles informations de la moto.
     * @return Une réponse HTTP avec le statut HTTP OK (200) et les informations mises à jour de la moto.
     */
    @PatchMapping("/{id}")
    ResponseEntity<MotoResponseAdminDto> modifier(
            @PathVariable("id") long id,
            @RequestBody MotoRequestDto motoRequestDto
    ){
        MotoResponseAdminDto reponse = motoService.modifier(id, motoRequestDto);
        return ResponseEntity.ok(reponse);
    }


}

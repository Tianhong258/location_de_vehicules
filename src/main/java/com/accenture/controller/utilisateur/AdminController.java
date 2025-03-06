package com.accenture.controller.utilisateur;

import com.accenture.service.AdminService;
import com.accenture.service.dto.utilisateur.AdminRequestDto;
import com.accenture.service.dto.utilisateur.AdminResponseDto;
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
import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admins")
@Tag(name = "Administrateurs", description = "Gestion des administrateurs")
public class AdminController {
    private final AdminService adminService;
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * <p>La méthode <code>ajouter</code> permet d'ajouter un nouvel administrateur.</p>
     *
     * @param adminRequestDto Les informations de l'administrateur à ajouter.
     * @return Une réponse HTTP avec un statut HTTP CREATED (201) et l'URI de la ressource créée.
     */
    @PostMapping
    @Operation(summary = "Ajouter un nouveau administrateur", description ="Ajoute un nouveau administrateur à la base de données")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Administrateur créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    ResponseEntity<Void> ajouter(@RequestBody @Valid AdminRequestDto adminRequestDto){
        log.info("L'ajoute de l'admin commence avec son email : {}", adminRequestDto.email());
        AdminResponseDto adminEnreg = adminService.ajouter(adminRequestDto);
        URI location = ServletUriComponentsBuilder//construire un uri
                .fromCurrentRequest()//l'uri actuel (localhost 8080 admin)
                .path("/{id}")//ajouter dynamique
                .buildAndExpand(adminEnreg.id())//remplacer {id} par l'id de cet admin
                .toUri();
        return ResponseEntity.created(location).build();
    }

    /**
     * <p>La méthode <code>admins</code> permet de récupérer la liste de tous les administrateurs.</p>
     *
     * @return Une liste d'objets <code>AdminResponseDto</code> représentant les administrateurs.
     */
    @GetMapping
    @Operation(summary = "Récupérer tous les administrateurs", description ="Récupération de tous les administrateurs depuis la base de données")
    @ApiResponse(responseCode = "200", description = "Récupération effectuée avec succès")
    List<AdminResponseDto> trouverTous(){
        log.info("La récupération de tous les admins commence");
        return adminService.trouverTous();
    }

    /**
     * <p>La méthode <code>trouver</code> permet de trouver les informations d'un administrateur avec ses identifiants.</p>
     *
     * @param principal Les informations de l'administrateur connecté
     * @return Une réponse HTTP avec le statut HTTP OK (200) et les informations de l'administrateur.
     */
    @GetMapping("/infos")
    @Operation(summary = "Récupérer les informations d'un administrateur", description ="Récupération des informations d'un administrateur depuis la base de données")
    @ApiResponse(responseCode = "200", description = "Récupération effectuée avec succès")
    ResponseEntity<AdminResponseDto> trouver(Principal principal){
        log.info("La récupération des informations de l'admin commence avec son email : {}", principal.getName());
        AdminResponseDto trouve = adminService.trouver(principal);
        return ResponseEntity.ok(trouve);
    }


    /**
     * <p>La méthode <code>supprimer</code> permet de supprimer un administrateur en fonction de son email et mot de passe.</p>
     *
     * @param principal Les informations de l'administrateur connecté
     * @return Une réponse HTTP avec le statut HTTP NO_CONTENT (204) si la suppression a réussi.
     */
    @DeleteMapping
    @Operation(summary = "Supprimer un administrateur", description ="Suppression d'un administrateur depuis la base de données")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Suppression effectuée avec succès"),
            @ApiResponse(responseCode = "400", description = "Impossible de supprimer le dernier administrateur")
    })
    ResponseEntity<Void> supprimer(Principal principal){
        log.info("La suppression de l'admin commence avec son email : {}", principal.getName());
        adminService.supprimer(principal);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    /**
     * <p>La méthode <code>modifierPartiellement</code> permet de modifier partiellement les informations d'un administrateur.</p>
     *
     * @param principal Les informations de l'administrateur connecté
     * @param adminRequestDto Les nouvelles informations partielles de l'administrateur.
     * @return Une réponse HTTP avec le statut HTTP OK (200) et les informations partiellement mises à jour de l'administrateur.
     */
    @PatchMapping
    @Operation(summary = "Modifier un administrateur", description ="Modification d'un administrateur dans la base de données")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Modification effectuée avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    ResponseEntity<AdminResponseDto> modifierPartiellement(
            Principal principal,
            @RequestBody AdminRequestDto adminRequestDto
    ){
        log.info("La modification de l'admin commence avec son email : {}", principal.getName());
        AdminResponseDto reponse = adminService.modifierPartiellement(principal, adminRequestDto);
        return ResponseEntity.ok(reponse);
    }

}

package com.accenture.controller.utilisateur;


import com.accenture.service.AdminService;
import com.accenture.service.dto.utilisateur.AdminRequestDto;
import com.accenture.service.dto.utilisateur.AdminResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admins")
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
    ResponseEntity<Void> ajouter(@RequestBody @Valid AdminRequestDto adminRequestDto){
        AdminResponseDto adminEnreg = adminService.ajouter(adminRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(adminEnreg.id())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    /**
     * <p>La méthode <code>admins</code> permet de récupérer la liste de tous les administrateurs.</p>
     *
     * @return Une liste d'objets <code>AdminResponseDto</code> représentant les administrateurs.
     */
    @GetMapping
    List<AdminResponseDto> admins(){
        return adminService.trouverTous();
    }

    /**
     * <p>La méthode <code>trouver</code> permet de trouver les informations d'un administrateur avec ses identifiants.</p>
     *
     * @param email L'email de l'administrateur.
     * @param password Le mot de passe de l'administrateur.
     * @return Une réponse HTTP avec le statut HTTP OK (200) et les informations de l'administrateur.
     */
    @GetMapping("/infos")
    ResponseEntity<AdminResponseDto> trouver(
            @RequestParam String email,
            @RequestParam String password
    ){
        AdminResponseDto trouve = adminService.trouver(email, password);
        return ResponseEntity.ok(trouve);
    }


    /**
     * <p>La méthode <code>supprimer</code> permet de supprimer un administrateur en fonction de son email et mot de passe.</p>
     *
     * @param email L'email de l'administrateur à supprimer.
     * @param password Le mot de passe de l'administrateur à supprimer.
     * @return Une réponse HTTP avec le statut HTTP NO_CONTENT (204) si la suppression a réussi.
     */
    @DeleteMapping
    ResponseEntity<Void> supprimer(
            @RequestParam String email,
            @RequestParam String password
    ){
        adminService.supprimer(email, password);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * <p>La méthode <code>modifier</code> permet de modifier les informations d'un administrateur existant.</p>
     *
     * @param email L'email de l'administrateur à modifier.
     * @param password Le mot de passe de l'administrateur à modifier.
     * @param adminRequestDto Les nouvelles informations de l'administrateur.
     * @return Une réponse HTTP avec le statut HTTP OK (200) et les informations mises à jour de l'administrateur.
     */
    @PutMapping
    ResponseEntity<AdminResponseDto> modifier(
            @RequestParam String email,
            @RequestParam String password,
            @RequestBody @Valid AdminRequestDto adminRequestDto
    ){
        AdminResponseDto reponse = adminService.modifier(email, password, adminRequestDto);
        return ResponseEntity.ok(reponse);
    }

    /**
     * <p>La méthode <code>modifierPartiellement</code> permet de modifier partiellement les informations d'un administrateur.</p>
     *
     * @param email L'email de l'administrateur à modifier.
     * @param password Le mot de passe de l'administrateur à modifier.
     * @param adminRequestDto Les nouvelles informations partielles de l'administrateur.
     * @return Une réponse HTTP avec le statut HTTP OK (200) et les informations partiellement mises à jour de l'administrateur.
     */
    @PatchMapping
    ResponseEntity<AdminResponseDto> modifierPartiellement(
            @RequestParam String email,
            @RequestParam String password,
            @RequestBody AdminRequestDto adminRequestDto
    ){
        AdminResponseDto reponse = adminService.modifierPartiellement(email, password, adminRequestDto);
        return ResponseEntity.ok(reponse);
    }

}

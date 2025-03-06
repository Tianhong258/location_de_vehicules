package com.accenture.controller.utilisateur;

import com.accenture.service.ClientService;
import com.accenture.service.dto.utilisateur.ClientRequestDto;
import com.accenture.service.dto.utilisateur.ClientResponseDto;
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
@RequestMapping("/clients")
@Tag(name = "Clients", description = "Gestion des clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * <p>La méthode <code>ajouter</code> permet d'ajouter un nouveau client.</p>
     *
     * @param clientRequestDto Les informations du client à ajouter.
     * @return Une réponse HTTP avec un statut HTTP CREATED (201) et l'URI de la ressource créée.
     */
    @PostMapping
    @Operation(summary = "Ajouter un nouveau client", description ="Ajoute un nouveau client à la base de données")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Client créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    ResponseEntity<Void> ajouter(@RequestBody @Valid ClientRequestDto clientRequestDto) {
        log.info("L'ajoute du client commence avec son email : {}", clientRequestDto.email());
        ClientResponseDto clientEnreg = clientService.ajouter(clientRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(clientEnreg.id())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    /**
     * <p>La méthode <code>trouver</code> permet de récupérer les informations d'un client en fonction de son email et mot de passe.</p>
     *
     * @param principal Les informations du client connecté
     * @return Une réponse HTTP avec le statut HTTP OK (200) et les informations du client trouvé.
     */
    @GetMapping("/infos")
    @Operation(summary = "Récupérer les informations d'un client", description ="Récupération des informations d'un client depuis la base de données")
    @ApiResponse(responseCode = "200", description = "Récupération effectuée avec succès")
    ResponseEntity<ClientResponseDto> trouver(Principal principal){
        log.info("La récupération des informations du client commence avec son email : {}", principal.getName());
        ClientResponseDto trouve = clientService.trouver(principal);
        return ResponseEntity.ok(trouve);
    }

    /**
     * <p>La méthode <code>clients</code> permet de récupérer la liste de tous les clients.</p>
     *
     * @return Une liste d'objets <code>ClientResponseDto</code> représentant tous les clients.
     */
    @GetMapping
    @Operation(summary = "Récupérer tous les clients", description ="Récupération de tous les clients depuis la base de données")
    @ApiResponse(responseCode = "200", description = "Récupération effectuée avec succès")

    List<ClientResponseDto> trouverTous(){
        log.info("La récupération de tous les clients commence");
        return clientService.trouverTous();
    }

    /**
     * <p>La méthode <code>desactiverOuSupprimer</code> permet de désactiver ou supprimer un client en fonction de son email et mot de passe.</p>
     *
     * @param principal Les informations du client connecté
     * @return Une réponse HTTP avec le statut HTTP OK (200) si l'opération a réussi.
     */
    @DeleteMapping
    @Operation(summary = "Supprimer ou désactiver un client", description ="Suppression d'un client ou modification l'actif d'un client en false")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Suppression effectuée avec succès"),
            @ApiResponse(responseCode = "400", description = "Impossible de supprimer le client")
    })
    ResponseEntity<Void> desactiverOuSupprimer(Principal principal){
        log.info("La suppression ou la désactivation du client commence avec son email : {}", principal.getName());
        clientService.desactiverOuSupprimer(principal);
        return ResponseEntity.ok().build();
    }


    /**
     * <p>La méthode <code>modifierPartiellement</code> permet de modifier partiellement les informations d'un client.</p>
     *
     * @param principal Les informations du client connecté
     * @param clientRequestDto Les nouvelles informations partielles du client.
     * @return Une réponse HTTP avec le statut HTTP OK (200) et les informations partiellement mises à jour du client.
     */
    @PatchMapping
    @Operation(summary = "Modifier un client", description ="Modification d'un client dans la base de données")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Modification effectuée avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    ResponseEntity<ClientResponseDto> modifierPartiellement(
            Principal principal,
            @RequestBody ClientRequestDto clientRequestDto
    ){
        log.info("La modification des informations du client commence avec son email : {}", principal.getName());
        ClientResponseDto reponse =clientService.modifierPartiellement(principal, clientRequestDto);
        return ResponseEntity.ok(reponse);
    }

}

package com.accenture.controller.utilisateur;

import com.accenture.service.ClientService;
import com.accenture.service.dto.utilisateur.ClientRequestDto;
import com.accenture.service.dto.utilisateur.ClientResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/clients")
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
    ResponseEntity<Void> ajouter(@RequestBody @Valid ClientRequestDto clientRequestDto) {
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
     * @param email L'email du client.
     * @param password Le mot de passe du client.
     * @return Une réponse HTTP avec le statut HTTP OK (200) et les informations du client trouvé.
     */
    @GetMapping("/infos")
    ResponseEntity<ClientResponseDto> trouver(
            @RequestParam String email,
            @RequestParam String password
    ){
        ClientResponseDto trouve = clientService.trouver(email, password);
        return ResponseEntity.ok(trouve);
    }

    /**
     * <p>La méthode <code>clients</code> permet de récupérer la liste de tous les clients.</p>
     *
     * @return Une liste d'objets <code>ClientResponseDto</code> représentant tous les clients.
     */
    @GetMapping
    List<ClientResponseDto> clients(){
        return clientService.trouverTous();
    }

    /**
     * <p>La méthode <code>desactiverOuSupprimer</code> permet de désactiver ou supprimer un client en fonction de son email et mot de passe.</p>
     *
     * @param email L'email du client à désactiver ou supprimer.
     * @param password Le mot de passe du client à désactiver ou supprimer.
     * @return Une réponse HTTP avec le statut HTTP OK (200) si l'opération a réussi.
     */
    @DeleteMapping
    ResponseEntity<Void> desactiverOuSupprimer(
            @RequestParam String email,
            @RequestParam String password
    ){
        clientService.desactiverOuSupprimer(email, password);
        return ResponseEntity.ok().build();
    }

    /**
     * <p>La méthode <code>modifier</code> permet de modifier les informations d'un client existant.</p>
     *
     * @param email L'email du client à modifier.
     * @param password Le mot de passe du client à modifier.
     * @param clientRequestDto Les nouvelles informations du client.
     * @return Une réponse HTTP avec le statut HTTP OK (200) et les informations mises à jour du client.
     */
    @PutMapping
    ResponseEntity<ClientResponseDto> modifier(
            @RequestParam String email,
            @RequestParam String password,
            @RequestBody @Valid ClientRequestDto clientRequestDto
    ){
        ClientResponseDto reponse = clientService.modifier(email, password, clientRequestDto);
        return ResponseEntity.ok(reponse);
    }

    /**
     * <p>La méthode <code>modifierPartiellement</code> permet de modifier partiellement les informations d'un client.</p>
     *
     * @param email L'email du client à modifier.
     * @param password Le mot de passe du client à modifier.
     * @param clientRequestDto Les nouvelles informations partielles du client.
     * @return Une réponse HTTP avec le statut HTTP OK (200) et les informations partiellement mises à jour du client.
     */
    @PatchMapping
    ResponseEntity<ClientResponseDto> modifierPartiellement(
            @RequestParam String email,
            @RequestParam String password,
            @RequestBody ClientRequestDto clientRequestDto
    ){
        ClientResponseDto reponse =clientService.modifierPartiellement(email, password, clientRequestDto);
        return ResponseEntity.ok(reponse);
    }

}

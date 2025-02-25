package com.accenture.controller;

import com.accenture.service.ClientService;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
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

    @GetMapping("/infos")
    ResponseEntity<ClientResponseDto> connecter(
            @RequestParam String email,
            @RequestParam String password
    ){
        ClientResponseDto trouve = clientService.trouver(email, password);
        return ResponseEntity.ok(trouve);
    }

    @GetMapping
    List<ClientResponseDto> clients(){
        return clientService.trouverTous();
    }

    @PatchMapping("/desactiver")
    ResponseEntity<Void> desactiver(
            @RequestParam String email,
            @RequestParam String password
    ){
        clientService.desactiverOuSupprimer(email, password);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    ResponseEntity<ClientResponseDto> modifier(
            @RequestParam String email,
            @RequestParam String password,
            @RequestBody @Valid ClientRequestDto clientRequestDto
    ){
        ClientResponseDto reponse = clientService.modifier(email, password, clientRequestDto);
        return ResponseEntity.ok(reponse);
    }

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

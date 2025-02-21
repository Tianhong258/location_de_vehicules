package com.accenture.controller;

import com.accenture.service.ClientService;
import com.accenture.service.dto.AdminResponseDto;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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

    @GetMapping
    ResponseEntity<ClientResponseDto> connecter(@RequestParam String email, @RequestParam String password){
        ClientResponseDto trouve = clientService.verifierEtTrouver(email, password);
        return ResponseEntity.ok(trouve);
    }

}

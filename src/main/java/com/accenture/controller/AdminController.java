package com.accenture.controller;


import com.accenture.service.AdminService;
import com.accenture.service.dto.AdminRequestDto;
import com.accenture.service.dto.AdminResponseDto;
import com.accenture.service.mapper.AdminMapper;
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
    private final AdminMapper adminMapper; //TODO : mapper est nécessaire ?


    public AdminController(AdminService adminService, AdminMapper adminMapper) {
        this.adminService = adminService;
        this.adminMapper = adminMapper;
    }

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

    @GetMapping
    List<AdminResponseDto> admins(){
        return adminService.trouverTous();
    }


    @GetMapping("/infos")
    ResponseEntity<AdminResponseDto> connecter(
            @RequestParam String email,
            @RequestParam String password
    ){
        AdminResponseDto trouve = adminService.trouver(email, password);
        return ResponseEntity.ok(trouve);
    }

    @DeleteMapping
    ResponseEntity<Void> supprimer(
            @RequestParam String email,
            @RequestParam String password
    ){
        adminService.supprimer(email, password);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PutMapping
    ResponseEntity<AdminResponseDto> modifier(
            @RequestParam String email,
            @RequestParam String password,
            @RequestBody @Valid AdminRequestDto adminRequestDto
    ){
        AdminResponseDto reponse = adminService.modifier(email, password, adminRequestDto);
        return ResponseEntity.ok(reponse);
    }
}

package com.accenture.controller;


import com.accenture.service.AdminService;
import com.accenture.service.dto.AdminRequestDto;
import com.accenture.service.dto.AdminResponseDto;
import com.accenture.service.mapper.AdminMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    private final AdminMapper adminMapper;


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
    ResponseEntity<AdminResponseDto> connecter(@RequestParam String email, @RequestParam String password){
        AdminResponseDto trouve = adminService.verifierEtTrouver(email, password);
        return ResponseEntity.ok(trouve);
    }


}

package com.accenture.controller.vehiculeController;

import com.accenture.service.VoitureService;
import com.accenture.service.dto.vehiculeDto.VoitureRequestDto;
import com.accenture.service.dto.vehiculeDto.VoitureResponseAdminDto;
import jakarta.validation.Valid;
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

    @GetMapping
    List<VoitureResponseAdminDto> trouverToutes(){
        return voitureService.trouverToutes();
    }

    @GetMapping("/{id}")
    ResponseEntity<VoitureResponseAdminDto> trouver(
           @PathVariable("id") long id
    ){
        VoitureResponseAdminDto trouve = voitureService.trouver(id);
        return ResponseEntity.ok(trouve);
    }

}

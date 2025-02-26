package com.accenture.controller.vehiculeController;

import com.accenture.model.Filtre;
import com.accenture.service.MotoService;
import com.accenture.service.dto.vehiculeDto.MotoRequestDto;
import com.accenture.service.dto.vehiculeDto.MotoResponseAdminDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("motos")
public class MotoController {
    private final MotoService motoService;

    public MotoController(MotoService motoService) {
        this.motoService = motoService;
    }

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

    @GetMapping
    List<MotoResponseAdminDto> trouverToutes(){
        return motoService.trouverToutes();
    }

    @GetMapping("/{id}")
    ResponseEntity<MotoResponseAdminDto> trouver(
            @PathVariable("id") long id
    ){
        MotoResponseAdminDto trouve = motoService.trouver(id);
        return ResponseEntity.ok(trouve);
    }

    @GetMapping("/filtrer")
    List<MotoResponseAdminDto> filtrer (@RequestParam Filtre filtre) {
        return motoService.filtrer(filtre);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> supprimer(@PathVariable("id") long id){
        motoService.supprimer(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}")
    ResponseEntity<MotoResponseAdminDto> modifier(
            @PathVariable("id") long id,
            @RequestBody MotoRequestDto motoRequestDto
    ){
        MotoResponseAdminDto reponse = motoService.modifier(id, motoRequestDto);
        return ResponseEntity.ok(reponse);
    }


}

package com.accenture.controller.vehicule;

import com.accenture.model.CategorieVehicule;
import com.accenture.model.Filtre;
import com.accenture.service.VehiculeService;
import com.accenture.service.dto.vehicule.VehiculeAdminDto;
import com.accenture.service.dto.vehicule.VehiculeClientDto;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/vehicules")
@Tag(name = "Véhicules", description = "Gestion des véhicules")

public class VehiculeController {
    private final VehiculeService vehiculeService;

    public VehiculeController(VehiculeService vehiculeService) {
        this.vehiculeService = vehiculeService;
    }
//TODO : java doc à ajouter
    @GetMapping("/filtrer")
    VehiculeAdminDto filtrer(
            @Parameter(description = "Condition de récupérer") @RequestParam Filtre filtre
    ){
        log.info("La recherche des véhicules commence avec la condition : {}", filtre);
        return vehiculeService.filtrer(filtre);
    }

    @GetMapping("/rechercher")
    VehiculeClientDto rechercherParDate(
            @Parameter(description = "Date du début de réservation") @RequestParam LocalDate debut,
            @Parameter(description = "Date de la fin de réservation") @RequestParam LocalDate fin,
            Principal principal,
            @Parameter(description = "Catégorie du véhicule recherché") @RequestParam(required = false)CategorieVehicule categorie
            ){
        log.info("La recherche des véhicules commence avec la catégorie : {}, depuis la date : {} jusqu'à la date : {}", categorie, debut, fin);
        return vehiculeService.rechercherParDate(debut,fin,categorie);
    }

//    @GetMapping
//    VehiculeAdminDto trouverTous(){
//        return vehiculeService.trouverTous();
//    }

}

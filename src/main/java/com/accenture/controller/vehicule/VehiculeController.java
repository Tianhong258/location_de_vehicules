package com.accenture.controller.vehicule;

import com.accenture.model.CategorieVehicule;
import com.accenture.model.Filtre;
import com.accenture.service.VehiculeService;
import com.accenture.service.dto.vehicule.VehiculeAdminDto;
import com.accenture.service.dto.vehicule.VehiculeClientDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;


@RestController
@RequestMapping("/vehicules")
public class VehiculeController {
    private final VehiculeService vehiculeService;

    public VehiculeController(VehiculeService vehiculeService) {
        this.vehiculeService = vehiculeService;
    }

    @GetMapping("/filtrer")
    VehiculeAdminDto filtrer(@RequestParam Filtre filtre){
        return vehiculeService.filtrer(filtre);
    }

    @GetMapping("/rechercher")
    VehiculeClientDto rechercherParDate(
            @RequestParam LocalDate debut,
            @RequestParam LocalDate fin,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) String email,
            @RequestParam(required = false)CategorieVehicule categorie
            ){
        return vehiculeService.rechercherParDate(debut,fin, password, email,categorie);
    }

//    @GetMapping
//    VehiculeAdminDto trouverTous(){
//        return vehiculeService.trouverTous();
//    }

}

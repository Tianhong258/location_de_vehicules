package com.accenture.service;


import com.accenture.exception.VehiculeException;
import com.accenture.model.*;
import com.accenture.repository.LocationDao;
import com.accenture.repository.VoitureDao;

import com.accenture.repository.entity.location.Location;
import com.accenture.repository.entity.vehicule.Voiture;

import com.accenture.service.dto.vehicule.VoitureRequestDto;
import com.accenture.service.dto.vehicule.VoitureResponseAdminDto;
import com.accenture.service.mapper.vehicule.VoitureMapper;
import jakarta.persistence.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class VoitureServiceImplTest {
    @Mock
    VoitureDao daoMock;

    @Mock
    VoitureMapper mapperMock;

    @Mock
    LocationDao daoLocationMock;

    @Mock
    Location locationMock;

    @InjectMocks
    VoitureServiceImpl service;


    @DisplayName("Test de la méthode ajouter(dto null) exception levée")
    @Test
    void testAjouterNull() {
        assertThrows(VehiculeException.class, () -> service.ajouter(null));
    }

    @DisplayName("Test de la méthode ajouter(avec marque null) exception levée")
    @Test
    void testAjouterAvecMarqueNull() {
        VoitureRequestDto dto = new VoitureRequestDto(null, "4", "Jaune", 4, TypeCarburant.ESSENCE, NombrePortesVoiture.TROIS, Transmission.MANUELLE,true, 2, TypeVoiture.FAMILIALE,100.0, 3000,true, false);
        assertThrows(VehiculeException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec marque blank) exception levée")
    @Test
    void testAjouterAvecMarqueBlank() {
        VoitureRequestDto dto = new VoitureRequestDto("      \n ", "4", "Jaune", 4, TypeCarburant.ESSENCE, NombrePortesVoiture.TROIS, Transmission.MANUELLE,true, 2, TypeVoiture.FAMILIALE,100.0, 3000,true, false);
        assertThrows(VehiculeException.class, () -> service.ajouter(dto));
    }
    @DisplayName("Test de la méthode ajouter(avec modele null) exception levée")
    @Test
    void testAjouterAvecModeleNull() {
        VoitureRequestDto dto = new VoitureRequestDto("Renault Twingo", null, "Jaune", 4, TypeCarburant.ESSENCE, NombrePortesVoiture.TROIS, Transmission.MANUELLE,true, 2, TypeVoiture.FAMILIALE,100.0, 3000,true, false);
        assertThrows(VehiculeException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec modele blank) exception levée")
    @Test
    void testAjouterAvecModeleBlank() {
        VoitureRequestDto dto = new VoitureRequestDto("Renault Twingo", "      \n ", "Jaune", 4, TypeCarburant.ESSENCE, NombrePortesVoiture.TROIS, Transmission.MANUELLE,true, 2, TypeVoiture.FAMILIALE,100.0, 3000,true, false);
        assertThrows(VehiculeException.class, () -> service.ajouter(dto));
    }
    @DisplayName("Test de la méthode ajouter(avec couleur null) exception levée")
    @Test
    void testAjouterAvecCouleurNull() {
        VoitureRequestDto dto = new VoitureRequestDto("Renault Twingo","4" , null, 4, TypeCarburant.ESSENCE, NombrePortesVoiture.TROIS, Transmission.MANUELLE,true, 2, TypeVoiture.FAMILIALE,100.0, 3000,true, false);
        assertThrows(VehiculeException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec couleur blank) exception levée")
    @Test
    void testAjouterAvecCouleurBlank() {
        VoitureRequestDto dto = new VoitureRequestDto("Renault Twingo", "4", "    \n   ", 4, TypeCarburant.ESSENCE, NombrePortesVoiture.TROIS, Transmission.MANUELLE,true, 2, TypeVoiture.FAMILIALE,100.0, 3000,true, false);
        assertThrows(VehiculeException.class, () -> service.ajouter(dto));
    }
    @DisplayName("Test de la méthode ajouter(avec nombrePlaces null) exception levée")
    @Test
    void testAjouterAvecNombrePlacesNull() {
        VoitureRequestDto dto = new VoitureRequestDto("Renault Twingo","4" , "Jaune", null, TypeCarburant.ESSENCE, NombrePortesVoiture.TROIS, Transmission.MANUELLE,true, 2, TypeVoiture.FAMILIALE,100.0, 3000,true, false);
        assertThrows(VehiculeException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec nombrePlaces négatif) exception levée")
    @Test
    void testAjouterAvecNombrePlacesNegatif() {
        VoitureRequestDto dto = new VoitureRequestDto("Renault Twingo", "4", "Jaune", -3, TypeCarburant.ESSENCE, NombrePortesVoiture.TROIS, Transmission.MANUELLE,true, 2, TypeVoiture.FAMILIALE,100.0, 3000,true, false);
        assertThrows(VehiculeException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec nombrePortes null) exception levée")
    @Test
    void testAjouterAvecNombrePortesNull() {
        VoitureRequestDto dto = new VoitureRequestDto("Renault Twingo","4" , "Jaune", 4, TypeCarburant.ESSENCE, null, Transmission.MANUELLE,true, 2, TypeVoiture.FAMILIALE,100.0, 3000,true, false);
        assertThrows(VehiculeException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec typeCarburant null) exception levée")
    @Test
    void testAjouterAvecTypeCarburantNull() {
        VoitureRequestDto dto = new VoitureRequestDto("Renault Twingo","4" , "Jaune", 4, null, NombrePortesVoiture.TROIS, Transmission.MANUELLE,true, 2, TypeVoiture.FAMILIALE,100.0, 3000,true, false);
        assertThrows(VehiculeException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec transmission null) exception levée")
    @Test
    void testAjouterAvecTransmissionNull() {
        VoitureRequestDto dto = new VoitureRequestDto("Renault Twingo","4" , "Jaune", 4, TypeCarburant.ESSENCE, NombrePortesVoiture.TROIS, null,true, 2, TypeVoiture.FAMILIALE,100.0, 3000,true, false);
        assertThrows(VehiculeException.class, () -> service.ajouter(dto));
    }
    @DisplayName("Test de la méthode ajouter(avec climatisation null) exception levée")
    @Test
    void testAjouterAvecClimatisationNull() {
        VoitureRequestDto dto = new VoitureRequestDto("Renault Twingo","4" , "Jaune", 4, TypeCarburant.ESSENCE, NombrePortesVoiture.TROIS, Transmission.MANUELLE,null, 2, TypeVoiture.FAMILIALE,100.0, 3000,true, false);
        assertThrows(VehiculeException.class, () -> service.ajouter(dto));
    }
    @DisplayName("Test de la méthode ajouter(avec nombreBagages null) exception levée")
    @Test
    void testAjouterAvecNombreBagagesNull() {
        VoitureRequestDto dto = new VoitureRequestDto("Renault Twingo","4" , "Jaune", 4, TypeCarburant.ESSENCE, NombrePortesVoiture.TROIS, Transmission.MANUELLE,true, null, TypeVoiture.FAMILIALE,100.0, 3000,true, false);
        assertThrows(VehiculeException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec nombreBagages négatif) exception levée")
    @Test
    void testAjouterAvecNombreBagagesNegatif() {
        VoitureRequestDto dto = new VoitureRequestDto("Renault Twingo", "4", "Jaune", 4, TypeCarburant.ESSENCE, NombrePortesVoiture.TROIS, Transmission.MANUELLE,true, -2, TypeVoiture.FAMILIALE,100.0, 3000,true, false);
        assertThrows(VehiculeException.class, () -> service.ajouter(dto));
    }
    @DisplayName("Test de la méthode ajouter(avec type null) exception levée")
    @Test
    void testAjouterAvecTypeNull() {
        VoitureRequestDto dto = new VoitureRequestDto("Renault Twingo","4" , "Jaune", 4, TypeCarburant.ESSENCE, NombrePortesVoiture.TROIS, Transmission.MANUELLE,true, 2, null,100.0, 3000,true, false);
        assertThrows(VehiculeException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec tarif null) exception levée")
    @Test
    void testAjouterAvecTarifNull() {
        VoitureRequestDto dto = new VoitureRequestDto("Renault Twingo","4" , "Jaune", 4, TypeCarburant.ESSENCE, NombrePortesVoiture.TROIS, Transmission.MANUELLE,true, 2, TypeVoiture.FAMILIALE,null, 3000,true, false);
        assertThrows(VehiculeException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec tarif négatif) exception levée")
    @Test
    void testAjouterAvecTarifNegatif() {
        VoitureRequestDto dto = new VoitureRequestDto("Renault Twingo", "4", "Jaune", 4, TypeCarburant.ESSENCE, NombrePortesVoiture.TROIS, Transmission.MANUELLE,true, 2, TypeVoiture.FAMILIALE,-100.0, 3000,true, false);
        assertThrows(VehiculeException.class, () -> service.ajouter(dto));
    }
    @DisplayName("Test de la méthode ajouter(avec kilometrage null) exception levée")
    @Test
    void testAjouterAvecKilometrageNull() {
        VoitureRequestDto dto = new VoitureRequestDto("Renault Twingo","4" , "Jaune", 4, TypeCarburant.ESSENCE, NombrePortesVoiture.TROIS, Transmission.MANUELLE,true, 2, TypeVoiture.FAMILIALE,100.0, null,true, false);
        assertThrows(VehiculeException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec kilometrage négatif) exception levée")
    @Test
    void testAjouterAvecKilometrageNegatif() {
        VoitureRequestDto dto = new VoitureRequestDto("Renault Twingo", "4", "Jaune", 4, TypeCarburant.ESSENCE, NombrePortesVoiture.TROIS, Transmission.MANUELLE,true, 2, TypeVoiture.FAMILIALE,100.0, -3000,true, false);
        assertThrows(VehiculeException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec actif null) exception levée")
    @Test
    void testAjouterAvecActifNull() {
        VoitureRequestDto dto = new VoitureRequestDto("Renault Twingo","4" , "Jaune", 4, TypeCarburant.ESSENCE, NombrePortesVoiture.TROIS, Transmission.MANUELLE,true, 2, TypeVoiture.FAMILIALE,100.0, 3000,null, false);
        assertThrows(VehiculeException.class, () -> service.ajouter(dto));
    }
    @DisplayName("Test de la méthode ajouter(avec retire null) exception levée")
    @Test
    void testAjouterAvecRetireNull() {
        VoitureRequestDto dto = new VoitureRequestDto("Renault Twingo","4" , "Jaune", 4, TypeCarburant.ESSENCE, NombrePortesVoiture.TROIS, Transmission.MANUELLE,true, 2, TypeVoiture.FAMILIALE,100.0, 3000,true, null);
        assertThrows(VehiculeException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec actif true, retire true) exception levée")
    @Test
    void testAjouterAvecActifTrueRetireTrue() {
        VoitureRequestDto dto = new VoitureRequestDto("Renault Twingo","4" , "Jaune", 4, TypeCarburant.ESSENCE, NombrePortesVoiture.TROIS, Transmission.MANUELLE,true, 2, TypeVoiture.FAMILIALE,100.0, null,true, true);
        assertThrows(VehiculeException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec nombrePlaces>9), la voiture enregistrée a un permis D1")
    @Test
    void testAjouterAvecNombrePlacesSuperieurA9() {
        VoitureResponseAdminDto voitureResponseAdminDto= creerVoitureResponseAdminDto2();
        VoitureRequestDto voitureRequestDto = creerVoitureRequestDto2();
        Voiture voitureApresEnreg = creerVoiture2();
        voitureApresEnreg.setId(0);
        Voiture voitureAvantEnreg = new Voiture();
        voitureAvantEnreg.setId(0);
        voitureAvantEnreg.setMarque("Honda");
        voitureAvantEnreg.setModele("e:HEV");
        voitureAvantEnreg.setCouleur("Rose");
        voitureAvantEnreg.setNombrePlaces(12);
        voitureAvantEnreg.setTypeCarburant(TypeCarburant.HYBRIDE);
        voitureAvantEnreg.setNombrePortes(NombrePortesVoiture.CINQ);
        voitureAvantEnreg.setTransmission(Transmission.AUTO);
        voitureAvantEnreg.setClimatisation(true);
        voitureAvantEnreg.setNombreBagages(100);
        voitureAvantEnreg.setTypeVoiture(TypeVoiture.LUXE);
        voitureAvantEnreg.setTarif(200.0);
        voitureAvantEnreg.setKilometrage(1000);
        voitureAvantEnreg.setActif(true);
        voitureAvantEnreg.setRetire(false);
        Mockito.when(mapperMock.toVoiture(voitureRequestDto)).thenReturn(voitureAvantEnreg);
        Mockito.when(daoMock.save(voitureAvantEnreg)).thenReturn(voitureApresEnreg);
        Mockito.when(mapperMock.toVoitureResponseAdminDto(voitureApresEnreg)).thenReturn(voitureResponseAdminDto);
        assertSame(voitureResponseAdminDto, service.ajouter(voitureRequestDto));
        assertEquals(Permis.D1, voitureAvantEnreg.getPermis());

    }
    @DisplayName("Test de la méthode ajouter(avec nombrePlaces<9), la voiture enregistrée a un permis B")
    @Test
    void testAjouterAvecNombrePlacesInferieurA9() {
        VoitureResponseAdminDto voitureResponseAdminDto= creerVoitureResponseAdminDto();
        VoitureRequestDto voitureRequestDto = creerVoitureRequestDto();
        Voiture voitureApresEnreg = creerVoiture();
        Voiture voitureAvantEnreg = new Voiture();
        voitureAvantEnreg.setId(0);
        voitureAvantEnreg.setMarque("Renault Twingo");
        voitureAvantEnreg.setModele( "4");
        voitureAvantEnreg.setCouleur("Jaune");
        voitureAvantEnreg.setNombrePlaces(4);
        voitureAvantEnreg.setTypeCarburant(TypeCarburant.ESSENCE);
        voitureAvantEnreg.setNombrePortes(NombrePortesVoiture.TROIS);
        voitureAvantEnreg.setTransmission(Transmission.MANUELLE);
        voitureAvantEnreg.setClimatisation(true);
        voitureAvantEnreg.setNombreBagages(2);
        voitureAvantEnreg.setTypeVoiture(TypeVoiture.FAMILIALE);
        voitureAvantEnreg.setTarif(100.0);
        voitureAvantEnreg.setKilometrage(3000);
        voitureAvantEnreg.setActif(true);
        voitureAvantEnreg.setRetire(false);
        Mockito.when(mapperMock.toVoiture(voitureRequestDto)).thenReturn(voitureAvantEnreg);
        Mockito.when(daoMock.save(voitureAvantEnreg)).thenReturn(voitureApresEnreg);
        Mockito.when(mapperMock.toVoitureResponseAdminDto(voitureApresEnreg)).thenReturn(voitureResponseAdminDto);
        assertSame(voitureResponseAdminDto, service.ajouter(voitureRequestDto));
        assertEquals(Permis.B, voitureAvantEnreg.getPermis());

    }

    @DisplayName("""
            Si ajouter(VoitureRequestDto ok), save() est appelé, et VoitureResponseDto est renvoyé
            """)
    @Test
    void testAjouterOk() {
        Voiture voitureAvantEnreg = creerVoiture();
        voitureAvantEnreg.setId(0);
        VoitureRequestDto voitureRequestDto = creerVoitureRequestDto();
        Voiture voitureApresEnreg = creerVoiture();
        VoitureResponseAdminDto voitureResponseDto = creerVoitureResponseAdminDto();
        Mockito.when(mapperMock.toVoiture(voitureRequestDto)).thenReturn(voitureAvantEnreg);
        Mockito.when(daoMock.save(voitureAvantEnreg)).thenReturn(voitureApresEnreg);
        Mockito.when(mapperMock.toVoitureResponseAdminDto(voitureApresEnreg)).thenReturn(voitureResponseDto);
        assertSame(voitureResponseDto, service.ajouter(voitureRequestDto));
        Mockito.verify(daoMock, Mockito.times(1)).save(voitureAvantEnreg);
    }

    @DisplayName("""
            Test de la méthode trouverToutes() renvoyer une liste de VoitureResponseAdminDto vide 
            lors qu'il n'y a pas de voiture dans la base
            """)
    @Test
    void testTrouverToutesSansVoitureEnBase(){
        List<VoitureResponseAdminDto> list = new ArrayList<>();
        assertEquals(list, service.trouverToutes());
    }

    @DisplayName("""
            Test de la méthode trouverToutes(ok) renvoyer une liste de VoitureResponseAdminDto
            lors qu'il y a une liste de voitures en base
            """)
    @Test
    void testTrouverToutesOk(){
        List<Voiture> listeVoitures = new ArrayList<>();
        listeVoitures.add(creerVoiture());
        listeVoitures.add(creerVoiture2());
        List<VoitureResponseAdminDto> listeDto = new ArrayList<>();
        listeDto.add(creerVoitureResponseAdminDto());
        listeDto.add(creerVoitureResponseAdminDto2());
        Mockito.when(daoMock.findByOrderByActifDescRetireDesc()).thenReturn(listeVoitures);
        Mockito.when(mapperMock.toVoitureResponseAdminDto(listeVoitures.getFirst())).thenReturn(listeDto.getFirst());
        Mockito.when(mapperMock.toVoitureResponseAdminDto(listeVoitures.get(1))).thenReturn(listeDto.get(1));
        assertEquals(listeDto, service.trouverToutes());
    }

    @DisplayName("""
            Test de la méthode filtrer(actif) renvoyer une liste de VoitureResponseAdminDto vide 
            lors qu'il n'y a pas de voiture en base
            """)
    @Test
    void testFiltrerSansVoitureEnBase(){
        List<VoitureResponseAdminDto> listeDto= new ArrayList<>();
        assertEquals(listeDto, service.filtrer(Filtre.ACTIF));
    }

    @DisplayName("""
            Le test de la méthode filtrer(actif) renvoie une liste de VoitureResponseAdminDto avec tous les éléments
            dont actif == true lorsqu'il y a des voitures en base
            """)
    @Test
    void testFiltrerActifOk(){
        List<VoitureResponseAdminDto> liste= new ArrayList<>();
        liste.add(creerVoitureResponseAdminDto());
        liste.add(creerVoitureResponseAdminDto2());
        List<Voiture> listeVoiture = new ArrayList<>();
        listeVoiture.add(creerVoiture());
        listeVoiture.add(creerVoiture2());
        Mockito.when(daoMock.findByActifTrue()).thenReturn(listeVoiture);
        Mockito.when(mapperMock.toVoitureResponseAdminDto(listeVoiture.getFirst())).thenReturn(liste.getFirst());
        Mockito.when(mapperMock.toVoitureResponseAdminDto(listeVoiture.get(1))).thenReturn(liste.get(1));
        assertEquals(liste, service.filtrer(Filtre.ACTIF));
    }


    @DisplayName("""
            Le test de la méthode filtrer(non actif) renvoie une liste de VoitureResponseAdminDto avec tous les éléments
            dont actif == false lorsqu'il y a des voitures en base
            """)
    @Test
    void testFiltrerNonActifOk(){
        List<VoitureResponseAdminDto> liste= new ArrayList<>();
        liste.add(creerVoitureResponseAdminDto3());
        liste.add(creerVoitureResponseAdminDto4());
        List<Voiture> listeVoiture = new ArrayList<>();
        listeVoiture.add(creerVoiture3());
        listeVoiture.add(creerVoiture4());
        Mockito.when(daoMock.findByActifFalse()).thenReturn(listeVoiture);
        Mockito.when(mapperMock.toVoitureResponseAdminDto(listeVoiture.getFirst())).thenReturn(liste.getFirst());
        Mockito.when(mapperMock.toVoitureResponseAdminDto(listeVoiture.get(1))).thenReturn(liste.get(1));
        assertEquals(liste, service.filtrer(Filtre.NON_ACTIF));
    }

    @DisplayName("""
            Le test de la méthode filtrer(retire) renvoie une liste de VoitureResponseAdminDto avec tous les éléments
            dont retire == true lorsqu'il y a des voitures en base
            """)
    @Test
    void testFiltrerRetireOk(){
        List<VoitureResponseAdminDto> liste= new ArrayList<>();
        liste.add(creerVoitureResponseAdminDto4());
        List<Voiture> listeVoiture = new ArrayList<>();
        listeVoiture.add(creerVoiture4());
        Mockito.when(daoMock.findByRetireTrue()).thenReturn(listeVoiture);
        Mockito.when(mapperMock.toVoitureResponseAdminDto(listeVoiture.getFirst())).thenReturn(liste.getFirst());
        assertEquals(liste, service.filtrer(Filtre.RETIRE));
    }

    @DisplayName("""
            Le test de la méthode filtrer(non retire) renvoie une liste de VoitureResponseAdminDto avec tous les éléments
            dont retire == false lorsqu'il y a des voitures en base
            """)
    @Test
    void testFiltrerNonRetireOk(){
        List<VoitureResponseAdminDto> liste= new ArrayList<>();
        liste.add(creerVoitureResponseAdminDto());
        liste.add(creerVoitureResponseAdminDto2());
        liste.add(creerVoitureResponseAdminDto3());
        List<Voiture> listeVoiture = new ArrayList<>();
        listeVoiture.add(creerVoiture());
        listeVoiture.add(creerVoiture2());
        listeVoiture.add(creerVoiture3());
        Mockito.when(daoMock.findByRetireFalse()).thenReturn(listeVoiture);
        Mockito.when(mapperMock.toVoitureResponseAdminDto(listeVoiture.getFirst())).thenReturn(liste.getFirst());
        Mockito.when(mapperMock.toVoitureResponseAdminDto(listeVoiture.get(1))).thenReturn(liste.get(1));
        Mockito.when(mapperMock.toVoitureResponseAdminDto(listeVoiture.get(2))).thenReturn(liste.get(2));
        assertEquals(liste, service.filtrer(Filtre.NON_RETIRE));
    }

    @DisplayName("Test de la méthode trouver(id n'existe pas en base) exception levée")
    @Test
    void testTrouverAvecIdExistePas() {
        Mockito.when(daoMock.findById(1L)).thenReturn(Optional.empty());
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.trouver(1L));
        assertEquals("POM POM POM, id non présent", ex.getMessage());
    }



    @DisplayName("Test de la méthode trouver(ok) qui doit renvoyer un VoitureResponseAdminDto")
    @Test
    void testTrouverOk() {
        Voiture v = creerVoiture();
        Optional<Voiture> optVoiture = Optional.of(v);
        Mockito.when(daoMock.findById(1L)).thenReturn(optVoiture);
        VoitureResponseAdminDto voitureResponseAdminDto = creerVoitureResponseAdminDto();
        Mockito.when(mapperMock.toVoitureResponseAdminDto(v)).thenReturn(voitureResponseAdminDto);
        assertSame(voitureResponseAdminDto, service.trouver(1L));
    }


    @DisplayName("""
            Test la méthode supprimer(avec id n'existe pas en base) exception levée
            """)
    @Test
    void testSupprimerAvecIdExistePas(){
        Mockito.when(daoMock.existsById(1L)).thenReturn(false);
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, ()->service.supprimerOuRetire(1L));
        assertEquals("POM POM POM, id non présent", ex.getMessage());
    }

    @DisplayName("""
            Test la méthode supprimer(avec une voiture associée à des locations), retire devient true, actif devient false, save() est appelé
            """)
    @Test
    void testDesativerAvecVoitureAssocieeDesLocations(){
        Voiture voitureEnreg = new Voiture();
        voitureEnreg.setId(1);
        voitureEnreg.setMarque("Renault Twingo");
        voitureEnreg.setModele( "4");
        voitureEnreg.setCouleur("Jaune");
        voitureEnreg.setNombrePlaces(4);
        voitureEnreg.setTypeCarburant(TypeCarburant.ESSENCE);
        voitureEnreg.setNombrePortes(NombrePortesVoiture.TROIS);
        voitureEnreg.setTransmission(Transmission.MANUELLE);
        voitureEnreg.setClimatisation(true);
        voitureEnreg.setNombreBagages(2);
        voitureEnreg.setTypeVoiture(TypeVoiture.FAMILIALE);
        voitureEnreg.setPermis(Permis.B);
        voitureEnreg.setTarif(100.0);
        voitureEnreg.setKilometrage(3000);
        voitureEnreg.setActif(false);
        voitureEnreg.setRetire(true);
        Optional<Voiture> optVoiture = Optional.of(creerVoiture());
        Mockito.when(daoMock.existsById(1L)).thenReturn(true);
        Mockito.when(daoMock.findById(1L)).thenReturn(optVoiture);
        Mockito.when(daoLocationMock.findByVehicule(optVoiture.get())).thenReturn(List.of(locationMock));
        service.supprimerOuRetire(1L);
        Mockito.verify(daoMock).save(voitureEnreg);
    }

    @DisplayName("""
            Test la méthode supprimer(avec une voiture non associée à des locations), delete() est appelé
            """)
    @Test
    void testSupprimerOk(){
        Optional<Voiture> optVoiture = Optional.of(creerVoiture());
        Mockito.when(daoMock.existsById(1L)).thenReturn(true);
        Mockito.when(daoMock.findById(1L)).thenReturn(optVoiture);
        Mockito.when(daoLocationMock.findByVehicule(optVoiture.get())).thenReturn(List.of());
        service.supprimerOuRetire(1L);
        Mockito.verify(daoMock).deleteById(1L);
    }

    @DisplayName("Test la méthode modifier(avec id n'existe pas en base) exception levée")
    @Test
    void testModifierAvecIdExistePas(){
        VoitureRequestDto voitureRequestDto = creerVoitureRequestDto();
        Mockito.when(daoMock.findById(1L)).thenReturn(Optional.empty());
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, ()->service.modifier(1L, voitureRequestDto));
        assertEquals("POM POM POM, id non présent", ex.getMessage());
    }
    @DisplayName("Test la méthode modifier(avec l'id d'une voiture.getRetire==true) exception levée")
    @Test
    void testModifierAvecVoitureRetire(){
        VoitureRequestDto voitureRequestDto = creerVoitureRequestDto();
        Voiture v = creerVoiture4();
        Optional<Voiture> optVoiture = Optional.of(v);
        Mockito.when(daoMock.findById(1L)).thenReturn(optVoiture);
        VehiculeException ex = assertThrows(VehiculeException.class, ()->service.modifier(1L, voitureRequestDto));
        assertEquals("Impossible de modifier une voiture retirée depuis le parc", ex.getMessage());
    }
    @DisplayName("Test la méthode modifier(avec voitureRequestDto == null) exception levée")
    @Test
    void testModifierAvecVoitureRequestDtoNull(){
        VoitureRequestDto voitureRequestDto = null;
        Voiture v = creerVoiture();
        Optional<Voiture> optVoiture = Optional.of(v);
        Mockito.when(daoMock.findById(1L)).thenReturn(optVoiture);
        Mockito.when(mapperMock.toVoiture(voitureRequestDto)).thenReturn(null);
        VehiculeException ex = assertThrows(VehiculeException.class, ()->service.modifier(1L, voitureRequestDto));
        assertEquals("la nouvelle voiture est null", ex.getMessage());
    }

    @DisplayName("Test la méthode modifier(avec marque blank) exception levée")
    @Test
    void testModifierAvecMarqueBlank(){
        VoitureRequestDto voitureRequestDto = new VoitureRequestDto("   \n ", null, "Jaune", null, null, null,null,null, null, null,null, null,null, null);
        Voiture voitureAModifier = creerVoiture();
        Voiture nouvelle = new Voiture();
        nouvelle.setMarque("   \n ");
        nouvelle.setCouleur("Jaune");
        Optional<Voiture> optVoiture = Optional.of(voitureAModifier);
        Mockito.when(daoMock.findById(1L)).thenReturn(optVoiture);
        Mockito.when(mapperMock.toVoiture(voitureRequestDto)).thenReturn(nouvelle);
        VehiculeException ex = assertThrows(VehiculeException.class, ()->service.modifier(1L, voitureRequestDto));
        assertEquals("la marque de la voiture est absente", ex.getMessage());
    }
   //TODO : ici ce sont les autres tests pour la méthode verifierEtRemplacer
    @DisplayName("""
            Test la méthode modifier(ok) qui renvoyer un objet voitureResponseAdminDto, save() est appelé
            """)
    @Test
    void testModifierOk(){
        Voiture voitureAModifier = creerVoiture2();
        Optional<Voiture> optVoiture = Optional.of(voitureAModifier);
        VoitureRequestDto voitureRequestDto = new VoitureRequestDto(null, null, "Jaune", 5, null,null, null,false, null, null,null, null,null, null);
        Voiture nouvelle = new Voiture();
        nouvelle.setId(2);
        nouvelle.setCouleur("Jaune");
        nouvelle.setNombrePlaces(5);
        nouvelle.setClimatisation(false);
        VoitureResponseAdminDto voitureResponseAdminDto = new VoitureResponseAdminDto(2,"Honda","e:HEV","Jaune",TypeCarburant.HYBRIDE,5,NombrePortesVoiture.CINQ,Transmission.AUTO,false,100,TypeVoiture.LUXE,Permis.B,200.0, 1000, true, false);
        Mockito.when(daoMock.findById(2L)).thenReturn(optVoiture);
        Mockito.when(mapperMock.toVoiture(voitureRequestDto)).thenReturn(nouvelle);
        Mockito.when(mapperMock.toVoitureResponseAdminDto(nouvelle)).thenReturn(voitureResponseAdminDto);
        Mockito.when(daoMock.save(optVoiture.get())).thenReturn(nouvelle);
        assertEquals(voitureResponseAdminDto,service.modifier(2L,voitureRequestDto));
        Mockito.verify(daoMock).save(optVoiture.get());
    }



    private static VoitureRequestDto creerVoitureRequestDto(){
        return new VoitureRequestDto(
                "Renault Twingo",
                "4",
                "Jaune",
                4,
                TypeCarburant.ESSENCE,
                NombrePortesVoiture.TROIS,
                Transmission.MANUELLE,
                true,
                2,
                TypeVoiture.FAMILIALE,
                100.0,
                3000,
                true,
                false
        );
    }
    private static VoitureRequestDto creerVoitureRequestDto2(){
        return new VoitureRequestDto(
                "Honda",
                "e:HEV",
                "Rose",
                12,
                TypeCarburant.HYBRIDE,
                NombrePortesVoiture.CINQ,
                Transmission.AUTO,
                true,
                100,
                TypeVoiture.LUXE,
                200.0,
                1000,
                true,
                false
        );
    }

        private static Voiture creerVoiture(){
            Voiture voiture = new Voiture();
            voiture.setId(1);
            voiture.setMarque("Renault Twingo");
            voiture.setModele( "4");
            voiture.setCouleur("Jaune");
            voiture.setNombrePlaces(4);
            voiture.setTypeCarburant(TypeCarburant.ESSENCE);
            voiture.setNombrePortes(NombrePortesVoiture.TROIS);
            voiture.setTransmission(Transmission.MANUELLE);
            voiture.setClimatisation(true);
            voiture.setNombreBagages(2);
            voiture.setTypeVoiture(TypeVoiture.FAMILIALE);
            voiture.setPermis(Permis.B);
            voiture.setTarif(100.0);
            voiture.setKilometrage(3000);
            voiture.setActif(true);
            voiture.setRetire(false);
            return voiture;
        }
    private static Voiture creerVoiture2(){
        Voiture voiture = new Voiture();
        voiture.setId(2);
        voiture.setMarque("Honda");
        voiture.setModele("e:HEV");
        voiture.setCouleur("Rose");
        voiture.setNombrePlaces(12);
        voiture.setTypeCarburant(TypeCarburant.HYBRIDE);
        voiture.setNombrePortes(NombrePortesVoiture.CINQ);
        voiture.setTransmission(Transmission.AUTO);
        voiture.setClimatisation(true);
        voiture.setNombreBagages(100);
        voiture.setTypeVoiture(TypeVoiture.LUXE);
        voiture.setPermis(Permis.D1);
        voiture.setTarif(200.0);
        voiture.setKilometrage(1000);
        voiture.setActif(true);
        voiture.setRetire(false);
        return voiture;
    }

    private static Voiture creerVoiture3(){
        Voiture voiture = new Voiture();
        voiture.setId(3);
        voiture.setMarque("Renault Twingo 2");
        voiture.setModele("3");
        voiture.setCouleur( "Noir");
        voiture.setNombrePlaces(12);
        voiture.setTypeCarburant(TypeCarburant.HYBRIDE);
        voiture.setNombrePortes(NombrePortesVoiture.CINQ);
        voiture.setTransmission(Transmission.AUTO);
        voiture.setClimatisation(true);
        voiture.setNombreBagages(100);
        voiture.setTypeVoiture(TypeVoiture.LUXE);
        voiture.setPermis(Permis.D1);
        voiture.setTarif(200.0);
        voiture.setKilometrage(1000);
        voiture.setActif(false);
        voiture.setRetire(false);
        return voiture;
    }

    private static Voiture creerVoiture4(){
        Voiture voiture = new Voiture();
        voiture.setId(4);
        voiture.setMarque("Honda 2");
        voiture.setModele( "56");
        voiture.setCouleur( "Bleu Claire");
        voiture.setNombrePlaces(4);
        voiture.setTypeCarburant(TypeCarburant.HYBRIDE);
        voiture.setNombrePortes(NombrePortesVoiture.CINQ);
        voiture.setTransmission(Transmission.AUTO);
        voiture.setClimatisation(true);
        voiture.setNombreBagages(100);
        voiture.setTypeVoiture(TypeVoiture.LUXE);
        voiture.setPermis(Permis.B);
        voiture.setTarif(200.0);
        voiture.setKilometrage(1000);
        voiture.setActif(false);
        voiture.setRetire(true);
        return voiture;
    }




    private static VoitureResponseAdminDto creerVoitureResponseAdminDto(){
        return new VoitureResponseAdminDto(
                1,
                "Renault Twingo",
                "4",
                "Jaune",
                TypeCarburant.ESSENCE,
                4,
                NombrePortesVoiture.TROIS,
                Transmission.MANUELLE,
                true,
                2,
                TypeVoiture.FAMILIALE,
                Permis.B,
                100.0,
                3000,
                true,
                false);
    }
    private static VoitureResponseAdminDto creerVoitureResponseAdminDto2(){
        return new VoitureResponseAdminDto(
                2,
                "Honda",
                "e:HEV",
                "Rose",
                TypeCarburant.HYBRIDE,
                12,
                NombrePortesVoiture.CINQ,
                Transmission.AUTO,
                true,
                100,
                TypeVoiture.LUXE,
                Permis.D1,
                200.0,
                1000,
                true,
                false
               );
    }
    private static VoitureResponseAdminDto creerVoitureResponseAdminDto3(){
        return new VoitureResponseAdminDto(
                3,
                "Renault Twingo 2",
                "3",
                "Noir",
                TypeCarburant.HYBRIDE,
                12,
                NombrePortesVoiture.CINQ,
                Transmission.AUTO,
                true,
                100,
                TypeVoiture.LUXE,
                Permis.D1,
                200.0,
                1000,
                false,
                false
        );
    }
    private static VoitureResponseAdminDto creerVoitureResponseAdminDto4(){
        return new VoitureResponseAdminDto(
                4,
                "Honda 2",
                "56",
                "Bleu Claire",
                TypeCarburant.HYBRIDE,
                4,
                NombrePortesVoiture.CINQ,
                Transmission.AUTO,
                true,
                100,
                TypeVoiture.LUXE,
                Permis.B,
                200.0,
                1000,
                false,
                true
        );
    }



}
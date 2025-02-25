package com.accenture.service;

import com.accenture.exception.AdminException;
import com.accenture.repository.AdminDao;
import com.accenture.repository.entity.utilisateur.Admin;
import com.accenture.service.dto.utilisateurDto.AdminRequestDto;
import com.accenture.service.dto.utilisateurDto.AdminResponseDto;
import com.accenture.service.mapper.utilisateurMapper.AdminMapper;
import jakarta.persistence.EntityNotFoundException;
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
class AdminServiceImplTest {

    @Mock
    AdminDao daoMock;

    @Mock
    AdminMapper mapperMock;

    @InjectMocks
    AdminServiceImpl service;

    @DisplayName("Test de la méthode ajouter(dto null) exception levée")
    @Test
    void testAjouterNull(){
        assertThrows(AdminException.class, ()->service.ajouter(null));
    }

    @DisplayName("Test de la méthode ajouter(avec nom null) exception levée")
    @Test
    void testAjouterAvecNomNull(){
        AdminRequestDto dto = new AdminRequestDto(null, "Tanya", "tanya@gmail.com","333Tanya@","Chien plus foufou à la maison");
        assertThrows(AdminException.class, ()->service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec nom blank) exception levée")
    @Test
    void testAjouterAvecNomBlank(){
        AdminRequestDto dto = new AdminRequestDto("  \n   ", "Tanya", "tanya@gmail.com","333Tanya@","Chien plus foufou à la maison");
        assertThrows(AdminException.class, ()->service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec prenom null) exception levée")
    @Test
    void testAjouterAvecPrenomNull(){
        AdminRequestDto dto = new AdminRequestDto("Huang", null, "tanya@gmail.com","333Tanya@","Chien plus foufou à la maison");
        assertThrows(AdminException.class, ()->service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec prenom blank) exception levée")
    @Test
    void testAjouterAvecPrenomBlank(){
        AdminRequestDto dto = new AdminRequestDto("Huang", " \n     ", "tanya@gmail.com","333Tanya@","Chien plus foufou à la maison");
        assertThrows(AdminException.class, ()->service.ajouter(dto));
    }


    @DisplayName("Test de la méthode ajouter(avec mail null) exception levée")
    @Test
    void testAjouterAvecMailNull(){
        AdminRequestDto dto = new AdminRequestDto("Huang", "Tanya",null,"333Tanya@","Chien plus foufou à la maison");
        assertThrows(AdminException.class, ()->service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec mail blank) exception levée")
    @Test
    void testAjouterAvecMailBlank(){
        AdminRequestDto dto = new AdminRequestDto("Huang", "Tanya", "   \n   ","333Tanya@","Chien plus foufou à la maison");
        assertThrows(AdminException.class, ()->service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec password null) exception levée")
    @Test
    void testAjouterAvecPasswordNull(){
        AdminRequestDto dto = new AdminRequestDto("Huang", "Tanya", "tanya@gmail.com",null,"Chien plus foufou à la maison");
        assertThrows(AdminException.class, ()->service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec password blank) exception levée")
    @Test
    void testAjouterAvecPasswordBlank(){
        AdminRequestDto dto = new AdminRequestDto("Huang", "Tanya", "tanya@gmail.com","  ","Chien plus foufou à la maison");
        assertThrows(AdminException.class, ()->service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec fonction null) exception levée")
    @Test
    void testAjouterAvecFonctionNull(){
        AdminRequestDto dto = new AdminRequestDto("Huang", "Tanya", "tanya@gmail.com","333Tanya@",null);
        assertThrows(AdminException.class, ()->service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec fonction blank) exception levée")
    @Test
    void testAjouterAvecFonctionBlank(){
        AdminRequestDto dto = new AdminRequestDto("Huang", "Tanya", "tanya@gmail.com","333Tanya@","     ");
        assertThrows(AdminException.class, ()->service.ajouter(dto));
    }

    //TODO : vérification de password et email

    @DisplayName("""
            Si ajouter(AdminRequestDto ok), save() est appelé, et AdminResponseDto est renvoyé
            """)
    @Test
    void testAjouterOk() {
        Admin adminAvantEnreg = creerAdmin();
        adminAvantEnreg.setId(0);
        AdminRequestDto adminRequestDto = new AdminRequestDto("Huang", "Tanya", "tanya@gmail.com","333Tanya@","Chien plus foufou à la maison");
        Admin adminApresEnreg = creerAdmin();
        AdminResponseDto adminResponseDto = creerAdminResponseDto();
        Mockito.when(mapperMock.toAdmin(adminRequestDto)).thenReturn(adminAvantEnreg);
        Mockito.when(daoMock.save(adminAvantEnreg)).thenReturn(adminApresEnreg);
        Mockito.when(mapperMock.toAdminResponseDto(adminApresEnreg)).thenReturn(adminResponseDto);
        assertSame(adminResponseDto, service.ajouter(adminRequestDto));
        Mockito.verify(daoMock, Mockito.times(1)).save(adminAvantEnreg);
    }


    @DisplayName("Test de la méthode trouver(email n'existe pas en base ou password n'est pas correct) exception levée")
    @Test
    void testTrouverAvecEmailOuPasswordIncorrect() {
        Mockito.when(daoMock.findByEmailAndPassword("tanya@gmail.com", "345Tanya@")).thenReturn(Optional.empty());
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.trouver("tanya@gmail.com", "345Tanya@"));
        assertEquals("Email n'existe pas ou password ne correspond pas", ex.getMessage());
    }



    @DisplayName("Test de la méthode trouver(ok) qui doit renvoyer un AdminResponseDto")
    @Test
    void testTrouverOk() {
        Admin a = creerAdmin();
        Optional<Admin> optAdmin = Optional.of(a);
        Mockito.when(daoMock.findByEmailAndPassword("tanya@gmail.com", "333Tanya@")).thenReturn(optAdmin);
        AdminResponseDto adminResponseDto = creerAdminResponseDto();
        Mockito.when(mapperMock.toAdminResponseDto(a)).thenReturn(adminResponseDto);
        assertSame(adminResponseDto, service.trouver("tanya@gmail.com","333Tanya@"));
    }

    @DisplayName("""
            Test de la méthode trouverTous() renvoyer une liste<AdminResponseDto> vide 
            lors qu'il n'y a pas d'administrateur dans la base
            """)
    @Test
    void testTrouverTousSansAdminEnBase(){
        List<AdminResponseDto> list = new ArrayList<>();
        assertEquals(list, service.trouverTous());
    }

    @DisplayName("""
            Test de la méthode trouverTous(ok) renvoyer une liste<AdminResponseDto>
            lors qu'il y a une liste d'admins en base
            """)
    @Test
    void testTrouverTousOk(){
        List<Admin> listeAdmin = new ArrayList<>();
        listeAdmin.add(creerAdmin());
        listeAdmin.add(creerAdmin2());
        List<AdminResponseDto> listeDto = new ArrayList<>();
        listeDto.add(creerAdminResponseDto());
        listeDto.add(creerAdminResponseDto2());
        Mockito.when(daoMock.findAll()).thenReturn(listeAdmin);
        Mockito.when(mapperMock.toAdminResponseDto(listeAdmin.getFirst())).thenReturn(listeDto.getFirst());
        Mockito.when(mapperMock.toAdminResponseDto(listeAdmin.get(1))).thenReturn(listeDto.get(1));
        assertEquals(listeDto, service.trouverTous());
    }

    @DisplayName("""
            Test la méthode supprimer(avec le dernier administrateur en base) exception levée
            """)
    @Test
    void testSupprimerAvecDernierAdmin(){
        Admin a = creerAdmin();
        Optional<Admin> optAdmin = Optional.of(a);
        Mockito.when(daoMock.findByEmailAndPassword("tanya@gmail.com", "333Tanya@")).thenReturn(optAdmin);
        AdminException ex = assertThrows(AdminException.class, ()->service.supprimer("tanya@gmail.com", "333Tanya@"));
        assertEquals("Interdit de supprimer le compte du dernier administrateur ! ", ex.getMessage());
    }

    @DisplayName("""
            Test la méthode supprimer(ok), delete() est appelé
            """)
    @Test
    void testSupprimerOk(){
        Admin a = creerAdmin();
        Optional<Admin> optAdmin = Optional.of(a);
        Mockito.when(daoMock.findByEmailAndPassword("tanya@gmail.com", "333Tanya@")).thenReturn(optAdmin);
        Mockito.when(daoMock.count()).thenReturn(Long.valueOf(2));
        service.supprimer("tanya@gmail.com", "333Tanya@");
        Mockito.verify(daoMock).delete(optAdmin.get());
    }

    @DisplayName("""
            Test la méthode modifier(ok) qui renvoyer un objet adminResponseDto, save() est appelé
            """)
    @Test
    void testModifierOk(){
        Admin a = creerAdmin();
        Optional<Admin> optAdmin = Optional.of(a);
        AdminRequestDto adminRequestDto = new AdminRequestDto("Huang", "Tanya", "tanya@gmail.com","333Tanya@","Chien le plus cool à la maison");
        Admin adminModifie= new Admin();
        adminModifie.setId(1);
        adminModifie.setNom("Huang");
        adminModifie.setPrenom("Tanya");
        adminModifie.setPassword("333Tanya@");
        adminModifie.setEmail("tanya@gmail.com");
        adminModifie.setFonction("Chien le plus cool à la maison");
        AdminResponseDto adminResponseDto = new AdminResponseDto(1,"Huang", "Tanya", "tanya@gmail.com","Chien le plus cool à la maison" );;
        Mockito.when(daoMock.findByEmailAndPassword("tanya@gmail.com", "333Tanya@")).thenReturn(optAdmin);
        Mockito.when(mapperMock.toAdmin(adminRequestDto)).thenReturn(adminModifie);
        Mockito.when(mapperMock.toAdminResponseDto(adminModifie)).thenReturn(adminResponseDto);
        assertEquals(adminResponseDto,service.modifier("tanya@gmail.com", "333Tanya@",adminRequestDto));
        Mockito.verify(daoMock).save(adminModifie);
    }

    @DisplayName("Test la méthode modifierPartiellement(avec nom blank) exception levée")
    @Test
    void testModifierPartiellementAvecNomBlank(){
        Admin admin = new Admin();
        admin.setId(1);
        admin.setNom(" \n");
        admin.setPrenom("Tanya");
        admin.setPassword("333Tanya@");
        admin.setEmail("tanya@gmail.com");
        admin.setFonction("Chien le plus foufou à la maison");
        AdminRequestDto adminRequestDto = new AdminRequestDto(" \n", "Tanya","tanya@gmail.com","333Tanya@","Chien le plus foufou à la maison");
        Mockito.when(daoMock.findByEmailAndPassword("tanya@gmail.com","333Tanya@")).thenReturn(Optional.of(creerAdmin()));
        Mockito.when(mapperMock.toAdmin(adminRequestDto)).thenReturn(admin);
        assertThrows(AdminException.class, ()->service.modifierPartiellement("tanya@gmail.com","333Tanya@",adminRequestDto));
    }

    //TODO : les tests remplacer à finir
    @DisplayName("""
            Test la méthode modifierPartiellement(ok) qui renvoyer un objet adminResponseDto, save() est appelé
            """)
    @Test
    void testModifierPartiellementOk(){
        Admin a = creerAdmin();
        Optional<Admin> optAdmin = Optional.of(a);
        AdminRequestDto adminRequestDto = new AdminRequestDto(null, "Tanya", "tanya@gmail.com",null,"Chien le plus cool à la maison");
        Admin nouveau = new Admin();
        nouveau.setId(1);
        nouveau.setPrenom("Tanya");
        nouveau.setEmail("tanya@gmail.com");
        nouveau.setFonction("Chien le plus cool à la maison");
        AdminResponseDto adminResponseDto = new AdminResponseDto(1,"Huang", "Tanya", "tanya@gmail.com","Chien le plus cool à la maison" );
        Mockito.when(daoMock.findByEmailAndPassword("tanya@gmail.com", "333Tanya@")).thenReturn(optAdmin);
        Mockito.when(mapperMock.toAdmin(adminRequestDto)).thenReturn(nouveau);
        Mockito.when(mapperMock.toAdminResponseDto(nouveau)).thenReturn(adminResponseDto);
        Mockito.when(daoMock.save(optAdmin.get())).thenReturn(nouveau);
        assertEquals(adminResponseDto,service.modifierPartiellement("tanya@gmail.com", "333Tanya@",adminRequestDto));
        Mockito.verify(daoMock).save(optAdmin.get());
    }

    private static Admin creerAdmin(){
        Admin admin = new Admin();
        admin.setId(1);
        admin.setNom("Huang");
        admin.setPrenom("Tanya");
        admin.setPassword("333Tanya@");
        admin.setEmail("tanya@gmail.com");
        admin.setFonction("Chien le plus foufou à la maison");
        return admin;
    }
    private static Admin creerAdmin2(){
        Admin admin = new Admin();
        admin.setId(2);
        admin.setNom("Huang");
        admin.setPrenom("Jaqen");
        admin.setPassword("333Jaqen@");
        admin.setEmail("jaqen@gmail.com");
        admin.setFonction("Chien le plus sage à la maison");
        return admin;
    }



    private static AdminResponseDto creerAdminResponseDto() {
        AdminResponseDto adminResponseDto = new AdminResponseDto(1,"Huang", "Tanya", "tanya@gmail.com","Chien le plus foufou à la maison" );
        return adminResponseDto;
    }

    private static AdminResponseDto creerAdminResponseDto2() {
        AdminResponseDto adminResponseDto = new AdminResponseDto(2,"Huang", "Jaqen", "jaqen@gmail.com","Chien le plus sage à la maison" );
        return adminResponseDto;
    }



}


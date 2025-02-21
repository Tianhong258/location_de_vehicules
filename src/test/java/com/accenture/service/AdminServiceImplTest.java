package com.accenture.service;

import com.accenture.exception.AdminException;
import com.accenture.repository.AdminDao;
import com.accenture.repository.entity.utilisateur.Admin;
import com.accenture.service.dto.AdminRequestDto;
import com.accenture.service.dto.AdminResponseDto;
import com.accenture.service.mapper.AdminMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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




    @DisplayName("Test de la méthode verifierEtTrouver(email n'existe pas en base) exception levée")
    @Test
    void testVerifierEtTrouverMailExistePas() {
        Mockito.when(daoMock.findByEmail("tanya@gmail.com")).thenReturn(Optional.empty());
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.verifierEtTrouver("tanya@gmail.com", "343Tanya@"));
        assertEquals("Impossible à trouver le mail", ex.getMessage());
    }

    @DisplayName("Test de la méthode verifierEtTrouver(password ne correspond pas l'email) exception levée")
    @Test
    void testVerifierEtTrouverPasswordIncorrect() {
        Mockito.when(daoMock.findByEmail("tanya@gmail.com")).thenReturn(Optional.of(creerAdmin()));
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.verifierEtTrouver("tanya@gmail.com", "33Tanya@"));
        assertEquals("Saisie de mot de passe est incorrecte", ex.getMessage());
    }

    @DisplayName("""
            Test de la méthode verifierEtTrouver(ok) qui doit renvoyer un AdminResponseDto
            lors que l'administrateur existe en base
            """)
    @Test
    void testVerifierEtTrouverExiste() {
        Admin a = creerAdmin();
        Optional<Admin> optAdmin = Optional.of(a);
        Mockito.when(daoMock.findByEmail("tanya@gmail.com")).thenReturn(optAdmin);
        AdminResponseDto adminResponseDto = creerAdminResponseDto();
        Mockito.when(mapperMock.toAdminResponseDto(a)).thenReturn(adminResponseDto);
        assertSame(adminResponseDto, service.verifierEtTrouver("tanya@gmail.com","333Tanya@"));
    }


    private static Admin creerAdmin(){
        Admin admin = new Admin();
        admin.setId(1);
        admin.setNom("Huang");
        admin.setPrenom("Tanya");
        admin.setPassword("333Tanya@");
        admin.setEmail("tanya@gmail.com");
        admin.setFonction("Chien plus foufou à la maison");
        return admin;
    }
    private static AdminResponseDto creerAdminResponseDto() {
        AdminResponseDto adminResponseDto = new AdminResponseDto(1,"Huang", "Tanya", "tanya@gmail.com","Chien plus foufou à la maison" );
        return adminResponseDto;
    }



}


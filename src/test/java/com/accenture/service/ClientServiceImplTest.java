package com.accenture.service;


import com.accenture.exception.ClientException;
import com.accenture.model.Permis;
import com.accenture.repository.ClientDao;
import com.accenture.repository.entity.utilisateur.Adresse;
import com.accenture.repository.entity.utilisateur.Client;
import com.accenture.service.dto.*;
import com.accenture.service.mapper.ClientMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {


    @Mock
    ClientDao daoMock;

    @Mock
    ClientMapper mapperMock;

    @InjectMocks
    ClientServiceImpl service;

    @DisplayName("Test de la méthode ajouter(dto null) exception levée")
    @Test
    void testAjouterNull() {
        assertThrows(ClientException.class, () -> service.ajouter(null));
    }

    @DisplayName("Test de la méthode ajouter(avec nom null) exception levée")
    @Test
    void testAjouterAvecNomNull() {
        ClientRequestDto dto = new ClientRequestDto(null, "Tanya", "tanya@gmail.com", "333Tanya@", new AdresseDto("parmi les tracteurs","44000","Jardin"), LocalDate.of(2000, 5,23), Arrays.asList(Permis.A, Permis.B));
        assertThrows(ClientException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec nom blank) exception levée")
    @Test
    void testAjouterAvecNomBlank() {
        ClientRequestDto dto = new ClientRequestDto("   ", "Tanya", "tanya@gmail.com", "333Tanya@", new AdresseDto("parmi les tracteurs","44000","Jardin"), LocalDate.of(2000, 5,23), Arrays.asList(Permis.A, Permis.B));
        assertThrows(ClientException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec prenom null) exception levée")
    @Test
    void testAjouterAvecPrenomNull() {
        ClientRequestDto dto = new ClientRequestDto("Huang", null, "tanya@gmail.com", "333Tanya@", new AdresseDto("parmi les tracteurs","44000","Jardin"), LocalDate.of(2000, 5,23), Arrays.asList(Permis.A, Permis.B));
        assertThrows(ClientException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec prenom blank) exception levée")
    @Test
    void testAjouterAvecPrenomBlank() {
        ClientRequestDto dto = new ClientRequestDto("Huang", "     ", "tanya@gmail.com", "333Tanya@", new AdresseDto("parmi les tracteurs","44000","Jardin"), LocalDate.of(2000, 5,23), Arrays.asList(Permis.A, Permis.B));
        assertThrows(ClientException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec mail null) exception levée")
    @Test
    void testAjouterAvecMailNull() {
        ClientRequestDto dto = new ClientRequestDto("Huang", "Tanya", null, "333Tanya@", new AdresseDto("parmi les tracteurs","44000","Jardin"), LocalDate.of(2000, 5,23), Arrays.asList(Permis.A, Permis.B));
        assertThrows(ClientException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec mail blank) exception levée")
    @Test
    void testAjouterAvecMailBlank() {
        ClientRequestDto dto = new ClientRequestDto("Huang", "Tanya", "      ", "333Tanya@", new AdresseDto("parmi les tracteurs","44000","Jardin"), LocalDate.of(2000, 5,23), Arrays.asList(Permis.A, Permis.B));
        assertThrows(ClientException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec password null) exception levée")
    @Test
    void testAjouterAvecPasswordNull() {
        ClientRequestDto dto = new ClientRequestDto("Huang", "Tanya", "tanya@gmail.com", null, new AdresseDto("parmi les tracteurs","44000","Jardin"), LocalDate.of(2000, 5,23), Arrays.asList(Permis.A, Permis.B));
        assertThrows(ClientException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec password blank) exception levée")
    @Test
    void testAjouterAvecPasswordBlank() {
        ClientRequestDto dto = new ClientRequestDto("Huang", "Tanya", "tanya@gmail.com", "      ", new AdresseDto("parmi les tracteurs","44000","Jardin"), LocalDate.of(2000, 5,23), Arrays.asList(Permis.A, Permis.B));
        assertThrows(ClientException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec adresse null) exception levée")
    @Test
    void testAjouterAvecAdresseNull() {
        ClientRequestDto dto = new ClientRequestDto("Huang", "Tanya", "tanya@gmail.com", "333Tanya@", null, LocalDate.of(2000, 5,23), Arrays.asList(Permis.A, Permis.B));
        assertThrows(ClientException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec rue null) exception levée")
    @Test
    void testAjouterAvecRueNull() {
        ClientRequestDto dto = new ClientRequestDto("Huang", "Tanya", "tanya@gmail.com", "333Tanya@", new AdresseDto(null,"44000","Jardin"), LocalDate.of(2000, 5,23), Arrays.asList(Permis.A, Permis.B));
        assertThrows(ClientException.class, () -> service.ajouter(dto));
    }
    @DisplayName("Test de la méthode ajouter(avec rue blank) exception levée")
    @Test
    void testAjouterAvecRueBlank() {
        ClientRequestDto dto = new ClientRequestDto("Huang", "Tanya", "tanya@gmail.com", "333Tanya@", new AdresseDto("      ","44000","Jardin"), LocalDate.of(2000, 5,23), Arrays.asList(Permis.A, Permis.B));
        assertThrows(ClientException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec codePostal null) exception levée")
    @Test
    void testAjouterAvecCodePostalNull() {
        ClientRequestDto dto = new ClientRequestDto("Huang", "Tanya", "tanya@gmail.com","333Tanya@", new AdresseDto("parmi les tracteurs",null,"Jardin"), LocalDate.of(2000, 5,23), Arrays.asList(Permis.A, Permis.B));
        assertThrows(ClientException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec codePostal blank) exception levée")
    @Test
    void testAjouterAvecCodePostalBlank() {
        ClientRequestDto dto = new ClientRequestDto("Huang", "Tanya", "tanya@gmail.com","333Tanya@", new AdresseDto("parmi les tracteurs","    ","Jardin"), LocalDate.of(2000, 5,23), Arrays.asList(Permis.A, Permis.B));
        assertThrows(ClientException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec ville null) exception levée")
    @Test
    void testAjouterAvecVilleNull() {
        ClientRequestDto dto = new ClientRequestDto("Huang", "Tanya", "tanya@gmail.com","333Tanya@", new AdresseDto("parmi les tracteurs","44000",null), LocalDate.of(2000, 5,23), Arrays.asList(Permis.A, Permis.B));
        assertThrows(ClientException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec ville blank) exception levée")
    @Test
    void testAjouterAvecVilleBlank() {
        ClientRequestDto dto = new ClientRequestDto("Huang", "Tanya", "tanya@gmail.com","333Tanya@", new AdresseDto("parmi les tracteurs","44000","    "), LocalDate.of(2000, 5,23), Arrays.asList(Permis.A, Permis.B));
        assertThrows(ClientException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec dateNaissance null) exception levée")
    @Test
    void testAjouterAvecDateNaissanceNull() {
        ClientRequestDto dto = new ClientRequestDto("Huang", "Tanya", "tanya@gmail.com","333Tanya@", new AdresseDto("parmi les tracteurs","44000","Jardin"), null, Arrays.asList(Permis.A, Permis.B));
        assertThrows(ClientException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec dateNaissance moins de 18 ans) exception levée")
    @Test
    void testAjouterAvecDateNaissanceMoins18Ans() {
        int nouvelleAnnee = LocalDate.now().getYear()-10;
        LocalDate dateNaissance = LocalDate.of(nouvelleAnnee,LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth());
        ClientRequestDto dto = new ClientRequestDto("Huang", "Tanya", "tanya@gmail.com","333Tanya@", new AdresseDto("parmi les tracteurs","44000","Jardin"), dateNaissance, Arrays.asList(Permis.A, Permis.B));
        assertThrows(ClientException.class, () -> service.ajouter(dto));
    }

    //TODO : vérification de password et email

    @DisplayName("""
            Si ajouter(ClientRequestDto ok), save() est appelé, et ClientResponseDto est renvoyé
            """)
    @Test
    void testAjouterOk() {
        Client clientAvantEnreg = creerClient();
        clientAvantEnreg.setId(0);
        ClientRequestDto clientRequestDto = clientRequestDto();
        Client clientApresEnreg = creerClient();
        ClientResponseDto clientResponseDto = clientResponseDto();
        Mockito.when(mapperMock.toClient(clientRequestDto)).thenReturn(clientAvantEnreg);
        Mockito.when(daoMock.save(clientAvantEnreg)).thenReturn(clientApresEnreg);
        Mockito.when(mapperMock.toClientResponseDto(clientApresEnreg)).thenReturn(clientResponseDto);
        assertSame(clientResponseDto, service.ajouter(clientRequestDto));
        Mockito.verify(daoMock, Mockito.times(1)).save(clientAvantEnreg);
    }

    @DisplayName("Test de la méthode trouver(email n'existe pas en base ou password n'est pas correct) exception levée")
    @Test
    void testTrouverAvecEmailOuPasswordIncorrect() {
        Mockito.when(daoMock.findByEmailAndPassword("tanya@gmail.com","345Tanya@")).thenReturn(Optional.empty());
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.trouver("tanya@gmail.com", "343Tanya@"));
        assertEquals("Email n'existe pas ou password ne correspond pas", ex.getMessage());
    }


    @DisplayName("Test de la méthode trouver(ok) qui doit renvoyer un ClientResponseDto")
    @Test
    void testTrouverOk() {
        Client c = creerClient();
        Optional<Client> optClient = Optional.of(c);
        Mockito.when(daoMock.findByEmailAndPassword("tanya@gmail.com","345Tanya@")).thenReturn(optClient);
        ClientResponseDto clientResponseDto = clientResponseDto();
        Mockito.when(mapperMock.toClientResponseDto(c)).thenReturn(clientResponseDto);
        assertSame(clientResponseDto, service.trouver("tanya@gmail.com", "333Tanya@"));
    }

    @DisplayName("""
            Test de la méthode trouverTous() renvoyer une liste<ClientResponseDto> vide 
            lors qu'il n'y a pas d'administrateur dans la base
            """)
    @Test
    void testTrouverTousSansClientEnBase(){
        List<ClientResponseDto> list = new ArrayList<>();
        assertEquals(list, service.trouverTous());
    }

    @DisplayName("""
            Test de la méthode trouverTous(ok) renvoyer une liste<ClientResponseDto>
            lors qu'il y a une liste de clients en base
            """)
    @Test
    void testTrouverTousOk(){
        List<Client> listeClient = new ArrayList<>();
        listeClient.add(creerClient());
        listeClient.add(creerClient2());
        List<ClientResponseDto> listeDto = new ArrayList<>();
        listeDto.add(clientResponseDto());
        listeDto.add(clientResponseDto2());
        Mockito.when(daoMock.findAll()).thenReturn(listeClient);
        Mockito.when(mapperMock.toClientResponseDto(listeClient.getFirst())).thenReturn(listeDto.getFirst());
        Mockito.when(mapperMock.toClientResponseDto(listeClient.get(1))).thenReturn(listeDto.get(1));
        assertEquals(listeDto, service.trouverTous());
    }

    //TODO : test à finir


    private static Client creerClient() {
        Client client= new Client();
        client.setId(1);
        client.setNom("Huang");
        client.setPrenom("Tanya");
        client.setPassword("333Tanya@");
        client.setEmail("tanya@gmail.com");
        client.setAdresse(creerAdresse());
        client.setDateNaissance(LocalDate.of(2000, 5,23));
        client.setListePermis(Arrays.asList(Permis.A, Permis.B));
        return client;
    }

    private static Client creerClient2() {
        Client client= new Client();
        client.setId(2);
        client.setNom("Huang");
        client.setPrenom("Jaqen");
        client.setPassword("333Jaqen@");
        client.setEmail("jaqen@gmail.com");
        client.setAdresse(creerAdresse());
        client.setDateNaissance(LocalDate.of(1999, 5,23));
        client.setListePermis(Arrays.asList(Permis.A, Permis.B));
        return client;
    }

    private static ClientResponseDto clientResponseDto(){
        return new ClientResponseDto(
                1,
                "Huang",
                "Tanya",
                "tanya@gmail.com",
                new AdresseDto("parmi les tracteurs","44000","Jardin"),
                LocalDate.of(2000, 5,23),
                LocalDate.now(),
                Arrays.asList(Permis.A, Permis.B));
    }

    private static ClientResponseDto clientResponseDto2(){
        return new ClientResponseDto(
                1,
                "Huang",
                "Jaqen",
                "jaqen@gmail.com",
                new AdresseDto("près de Tanya","44000","Jardin"),
                LocalDate.of(1999, 5,23),
                LocalDate.now(),
                Arrays.asList(Permis.A, Permis.B));
    }

    private static ClientRequestDto clientRequestDto(){
        return new ClientRequestDto(
                "Huang",
                "Tanya",
                "tanya@gmail.com",
                "333Tanya@",
                new AdresseDto("parmi les tracteurs","44000","Jardin"),
                LocalDate.of(2000, 5,23),
                Arrays.asList(Permis.A, Permis.B));
    }
    private static Adresse creerAdresse() {
        Adresse adresse = new Adresse();
        adresse.setId(1);
        adresse.setRue("parmi les tracteurs");
        adresse.setVille("Jardin");
        adresse.setCodePostal("44000");
        return adresse;
    }

}
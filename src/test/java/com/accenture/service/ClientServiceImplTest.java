package com.accenture.service;


import com.accenture.exception.UtilisateurException;
import com.accenture.model.Permis;
import com.accenture.repository.ClientDao;
import com.accenture.repository.entity.utilisateur.Adresse;
import com.accenture.repository.entity.utilisateur.Client;
import com.accenture.service.dto.utilisateur.AdresseDto;
import com.accenture.service.dto.utilisateur.ClientRequestDto;
import com.accenture.service.dto.utilisateur.ClientResponseDto;
import com.accenture.service.mapper.utilisateur.ClientMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;
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

    @Mock
    Principal principalMock;

    @Mock
    PasswordEncoder passwordEncoderMock;

    @InjectMocks
    ClientServiceImpl service;

    @DisplayName("Test de la méthode ajouter(dto null) exception levée")
    @Test
    void testAjouterNull() {
        assertThrows(UtilisateurException.class, () -> service.ajouter(null));
    }

    @DisplayName("Test de la méthode ajouter(avec nom null) exception levée")
    @Test
    void testAjouterAvecNomNull() {
        ClientRequestDto dto = new ClientRequestDto(null, "Jean-Jacques", "JJClient@gmail.com", "333JJClient@", new AdresseDto("33 Rue Victor Hugo","44000","Nantes"), LocalDate.of(2000, 5,23), Arrays.asList(Permis.A, Permis.B));
        assertThrows(UtilisateurException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec nom blank) exception levée")
    @Test
    void testAjouterAvecNomBlank() {
        ClientRequestDto dto = new ClientRequestDto("   ", "Jean-Jacques", "JJClient@gmail.com", "333JJClient@", new AdresseDto("33 Rue Victor Hugo","44000","Nantes"), LocalDate.of(2000, 5,23), Arrays.asList(Permis.A, Permis.B));
        assertThrows(UtilisateurException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec prenom null) exception levée")
    @Test
    void testAjouterAvecPrenomNull() {
        ClientRequestDto dto = new ClientRequestDto("Legrand", null, "JJClient@gmail.com", "333JJClient@", new AdresseDto("33 Rue Victor Hugo","44000","Nantes"), LocalDate.of(2000, 5,23), Arrays.asList(Permis.A, Permis.B));
        assertThrows(UtilisateurException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec prenom blank) exception levée")
    @Test
    void testAjouterAvecPrenomBlank() {
        ClientRequestDto dto = new ClientRequestDto("Legrand", "     ", "JJClient@gmail.com", "333JJClient@", new AdresseDto("33 Rue Victor Hugo","44000","Nantes"), LocalDate.of(2000, 5,23), Arrays.asList(Permis.A, Permis.B));
        assertThrows(UtilisateurException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec mail null) exception levée")
    @Test
    void testAjouterAvecMailNull() {
        ClientRequestDto dto = new ClientRequestDto("Legrand", "Jean-Jacques", null, "333JJClient@", new AdresseDto("33 Rue Victor Hugo","44000","Nantes"), LocalDate.of(2000, 5,23), Arrays.asList(Permis.A, Permis.B));
        assertThrows(UtilisateurException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec mail blank) exception levée")
    @Test
    void testAjouterAvecMailBlank() {
        ClientRequestDto dto = new ClientRequestDto("Legrand", "Jean-Jacques", "      ", "333JJClient@", new AdresseDto("33 Rue Victor Hugo","44000","Nantes"), LocalDate.of(2000, 5,23), Arrays.asList(Permis.A, Permis.B));
        assertThrows(UtilisateurException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec password null) exception levée")
    @Test
    void testAjouterAvecPasswordNull() {
        ClientRequestDto dto = new ClientRequestDto("Legrand", "Jean-Jacques", "JJClient@gmail.com", null, new AdresseDto("33 Rue Victor Hugo","44000","Nantes"), LocalDate.of(2000, 5,23), Arrays.asList(Permis.A, Permis.B));
        assertThrows(UtilisateurException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec password blank) exception levée")
    @Test
    void testAjouterAvecPasswordBlank() {
        ClientRequestDto dto = new ClientRequestDto("Legrand", "Jean-Jacques", "JJClient@gmail.com", "      ", new AdresseDto("33 Rue Victor Hugo","44000","Nantes"), LocalDate.of(2000, 5,23), Arrays.asList(Permis.A, Permis.B));
        assertThrows(UtilisateurException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec adresse null) exception levée")
    @Test
    void testAjouterAvecAdresseNull() {
        ClientRequestDto dto = new ClientRequestDto("Legrand", "Jean-Jacques", "JJClient@gmail.com", "333JJClient@", null, LocalDate.of(2000, 5,23), Arrays.asList(Permis.A, Permis.B));
        assertThrows(UtilisateurException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec rue null) exception levée")
    @Test
    void testAjouterAvecRueNull() {
        ClientRequestDto dto = new ClientRequestDto("Legrand", "Jean-Jacques", "JJClient@gmail.com", "333JJClient@", new AdresseDto(null,"44000","Nantes"), LocalDate.of(2000, 5,23), Arrays.asList(Permis.A, Permis.B));
        assertThrows(UtilisateurException.class, () -> service.ajouter(dto));
    }
    @DisplayName("Test de la méthode ajouter(avec rue blank) exception levée")
    @Test
    void testAjouterAvecRueBlank() {
        ClientRequestDto dto = new ClientRequestDto("Legrand", "Jean-Jacques", "JJClient@gmail.com", "333JJClient@", new AdresseDto("      ","44000","Nantes"), LocalDate.of(2000, 5,23), Arrays.asList(Permis.A, Permis.B));
        assertThrows(UtilisateurException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec codePostal null) exception levée")
    @Test
    void testAjouterAvecCodePostalNull() {
        ClientRequestDto dto = new ClientRequestDto("Legrand", "Jean-Jacques", "JJClient@gmail.com","333JJClient@", new AdresseDto("33 Rue Victor Hugo",null,"Nantes"), LocalDate.of(2000, 5,23), Arrays.asList(Permis.A, Permis.B));
        assertThrows(UtilisateurException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec codePostal blank) exception levée")
    @Test
    void testAjouterAvecCodePostalBlank() {
        ClientRequestDto dto = new ClientRequestDto("Legrand", "Jean-Jacques", "JJClient@gmail.com","333JJClient@", new AdresseDto("33 Rue Victor Hugo","    ","Nantes"), LocalDate.of(2000, 5,23), Arrays.asList(Permis.A, Permis.B));
        assertThrows(UtilisateurException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec ville null) exception levée")
    @Test
    void testAjouterAvecVilleNull() {
        ClientRequestDto dto = new ClientRequestDto("Legrand", "Jean-Jacques", "JJClient@gmail.com","333JJClient@", new AdresseDto("33 Rue Victor Hugo","44000",null), LocalDate.of(2000, 5,23), Arrays.asList(Permis.A, Permis.B));
        assertThrows(UtilisateurException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec ville blank) exception levée")
    @Test
    void testAjouterAvecVilleBlank() {
        ClientRequestDto dto = new ClientRequestDto("Legrand", "Jean-Jacques", "JJClient@gmail.com","333JJClient@", new AdresseDto("33 Rue Victor Hugo","44000","    "), LocalDate.of(2000, 5,23), Arrays.asList(Permis.A, Permis.B));
        assertThrows(UtilisateurException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec dateNaissance null) exception levée")
    @Test
    void testAjouterAvecDateNaissanceNull() {
        ClientRequestDto dto = new ClientRequestDto("Legrand", "Jean-Jacques", "JJClient@gmail.com","333JJClient@", new AdresseDto("33 Rue Victor Hugo","44000","Nantes"), null, Arrays.asList(Permis.A, Permis.B));
        assertThrows(UtilisateurException.class, () -> service.ajouter(dto));
    }

    @DisplayName("Test de la méthode ajouter(avec dateNaissance moins de 18 ans) exception levée")
    @Test
    void testAjouterAvecDateNaissanceMoins18Ans() {
        int nouvelleAnnee = LocalDate.now().getYear()-10;
        LocalDate dateNaissance = LocalDate.of(nouvelleAnnee,LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth());
        ClientRequestDto dto = new ClientRequestDto("Legrand", "Jean-Jacques", "JJClient@gmail.com","333JJClient@", new AdresseDto("33 Rue Victor Hugo","44000","Nantes"), dateNaissance, Arrays.asList(Permis.A, Permis.B));
        assertThrows(UtilisateurException.class, () -> service.ajouter(dto));
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


    @DisplayName("Test de la méthode trouver(ok) qui doit renvoyer un ClientResponseDto")
    @Test
    void testTrouverOk() {
        Client c = creerClient();
        Optional<Client> optClient = Optional.of(c);
        Mockito.when(daoMock.findByEmail("JJClient@gmail.com")).thenReturn(optClient);
        ClientResponseDto clientResponseDto = clientResponseDto();
        Mockito.when(mapperMock.toClientResponseDto(c)).thenReturn(clientResponseDto);
        Mockito.when(principalMock.getName()).thenReturn("JJClient@gmail.com");
        assertSame(clientResponseDto, service.trouver(principalMock));
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

    //TODO : test à finir, mais j'abandonne, admin est bien fait !


    private static Client creerClient() {
        Client client= new Client();
        client.setId(1);
        client.setNom("Legrand");
        client.setPrenom("Jean-Jacques");
        client.setPassword("333JJClient@");
        client.setEmail("JJClient@gmail.com");
        client.setAdresse(creerAdresse());
        client.setDateNaissance(LocalDate.of(2000, 5,23));
        client.setListePermis(Arrays.asList(Permis.A, Permis.A1,Permis.A2, Permis.B));
        return client;
    }

    private static Client creerClient2() {
        Client client= new Client();
        client.setId(2);
        client.setNom("Legrand");
        client.setPrenom("Jean-Marie");
        client.setPassword("333JMClient@");
        client.setEmail("JMClient@gmail.com");
        client.setAdresse(creerAdresse());
        client.setDateNaissance(LocalDate.of(1999, 5,23));
        client.setListePermis(Arrays.asList(Permis.A, Permis.A1,Permis.A2, Permis.B));
        return client;
    }

    private static ClientResponseDto clientResponseDto(){
        return new ClientResponseDto(
                1,
                "Legrand",
                "Jean-Jacques",
                "JJClient@gmail.com",
                new AdresseDto("33 Rue Victor Hugo","44000","Nantes"),
                LocalDate.of(2000, 5,23),
                LocalDate.now(),
                Arrays.asList(Permis.A,  Permis.A1,Permis.A2,Permis.B));
    }

    private static ClientResponseDto clientResponseDto2(){
        return new ClientResponseDto(
                1,
                "Legrand",
                "Jean-Marie",
                "JMClient@gmail.com",
                new AdresseDto("35 Rue Victor Hugo","44400","Rezé"),
                LocalDate.of(1999, 5,23),
                LocalDate.now(),
                Arrays.asList(Permis.A,  Permis.A1,Permis.A2,Permis.B));
    }

    private static ClientRequestDto clientRequestDto(){
        return new ClientRequestDto(
                "Legrand",
                "Jean-Jacques",
                "JJClient@gmail.com",
                "333JJClient@",
                new AdresseDto("33 Rue Victor Hugo","44000","Nantes"),
                LocalDate.of(2000, 5,23),
                Arrays.asList(Permis.A,  Permis.A1,Permis.A2,Permis.B));
    }
    private static Adresse creerAdresse() {
        Adresse adresse = new Adresse();
        adresse.setId(1);
        adresse.setRue("33 Rue Victor Hugo");
        adresse.setVille("Nantes");
        adresse.setCodePostal("44000");
        return adresse;
    }

}
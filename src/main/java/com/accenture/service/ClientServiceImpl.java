package com.accenture.service;


import com.accenture.exception.UtilisateurException;
import com.accenture.repository.ClientDao;
import com.accenture.repository.entity.utilisateur.Adresse;
import com.accenture.repository.entity.utilisateur.Client;
import com.accenture.service.dto.utilisateur.ClientRequestDto;
import com.accenture.service.dto.utilisateur.ClientResponseDto;
import com.accenture.service.mapper.utilisateur.ClientMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;


@Service
public class ClientServiceImpl implements ClientService {

    private final ClientDao clientDao;
    private final ClientMapper clientMapper;
    private static final Pattern passwordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[&\\#@\\-_%§]).{6,}$");
    private final PasswordEncoder passwordEncoder;


    public ClientServiceImpl(ClientDao clientDao, ClientMapper clientMapper, PasswordEncoder passwordEncoder) {
        this.clientDao = clientDao;
        this.clientMapper = clientMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * <p>La méthode <code>ajouter</code> permet d'ajouter un nouveau client.</p>
     *
     * @param clientRequestDto Les informations du client à ajouter.
     * @return Une réponse contenant les informations du client créé.
     * @throws UtilisateurException Si les données du client sont invalides.
     */

    @Override
    public ClientResponseDto ajouter(ClientRequestDto clientRequestDto) throws UtilisateurException {
        verifierClientRequestDto(clientRequestDto);
        Client client = clientMapper.toClient(clientRequestDto);
        String passWordChiffre = passwordEncoder.encode(client.getPassword());
        client.setPassword(passWordChiffre);
        Client clientEnreg = clientDao.save(client);
        return clientMapper.toClientResponseDto(clientEnreg);
    }

    /**
     * <p>La méthode <code>trouver</code> permet de récupérer un client avec son email et mot de passe.</p>
     *
     * @param principal Les informations du client connecté
     * @return Une réponse contenant les informations du client.
     */
    @Override
    public ClientResponseDto trouver(Principal principal) {
        Client client = clientDao.findByEmail(principal.getName()).orElseThrow();
        return clientMapper.toClientResponseDto(client);
    }

    /**
     * <p>La méthode <code>trouverTous</code> permet de récupérer tous les clients.</p>
     *
     * @return Une liste contenant les informations de tous les clients.
     */
    @Override
    public List<ClientResponseDto> trouverTous() {
        return clientDao.findAll()
                .stream()
                .map(clientMapper::toClientResponseDto)
                .toList();
    }


    /**
     * <p>La méthode <code>desactiverOuSupprimer</code> permet de désactiver ou supprimer un client.</p>
     *
     * @param principal Les informations du client connecté
     * @throws UtilisateurException Si le client ne peut pas être désactivé ou supprimé.
     */
    @Override
    public void desactiverOuSupprimer(Principal principal) throws UtilisateurException {
        Client client = clientDao.findByEmail(principal.getName()).orElseThrow();
        clientDao.delete(client);
        //trouver les locations, s'il y a pas
        // l'utilisateur peut supprimer son compte : créer supprimer() et desactiver()
//        client.setDesactive(true);
//        clientDao.save(client);
        //clientDao.deleteByEmail(clientResponseDto.email());
    }

    /**
     * <p>La méthode <code>modifierPartiellement</code> permet de modifier partiellement les informations d'un client.</p>
     *
     * @param principal        Les informations du client connecté
     * @param clientRequestDto Les nouvelles informations du client.
     * @return Une réponse contenant les informations mises à jour du client.
     * @throws UtilisateurException Si les données du client sont invalides.
     */
    @Override
    public ClientResponseDto modifierPartiellement(Principal principal, ClientRequestDto clientRequestDto) throws UtilisateurException {
        Client clientAModifier = clientDao.findByEmail(principal.getName()).orElseThrow();
        Client nouveau = clientMapper.toClient(clientRequestDto);
        verifierEtRemplacer(nouveau, clientAModifier);
        Client clientEnreg = clientDao.save(clientAModifier);
        return clientMapper.toClientResponseDto(clientEnreg);
    }


    private void verifierEtRemplacer(Client client, Client clientAModifier) throws UtilisateurException {
        if (client == null)
            throw new UtilisateurException("l'client est nulle");
        String clientNom = client.getNom();
        String clientPrenom = client.getPrenom();
        String clientEmail = client.getEmail();
        String clientPassword = client.getPassword();
        Adresse clientAdresse = client.getAdresse();
        LocalDate clientDateNaissance = client.getDateNaissance();
        if (clientNom != null) {
            if (clientNom.isBlank())
                throw new UtilisateurException("le nom du client est absent");
            clientAModifier.setNom(clientNom);
        }
        if (clientPrenom != null) {
            if (clientPrenom.isBlank())
                throw new UtilisateurException("le prénom du client est absent");
            clientAModifier.setPrenom(clientPrenom);
        }
        verifierEtRamplacerEmailPassword(clientAModifier, clientEmail, clientPassword);
        if (clientAdresse != null) {
            verifierEtRemplacerAdresse(clientAModifier, clientAdresse);
        }
        if (clientDateNaissance != null) {
            int nouvelleAnnee = clientDateNaissance.getYear() + 18;
            LocalDate nouvelleDate = LocalDate.of(nouvelleAnnee, clientDateNaissance.getMonth(), clientDateNaissance.getDayOfMonth());
            if (nouvelleDate.isAfter(LocalDate.now()))
                throw new UtilisateurException("pour rester s'inscrire sur notre site, il faut au moins 18 ans");
            clientAModifier.setDateNaissance(clientDateNaissance);
        }

    }

    private static void verifierEtRemplacerAdresse(Client clientAModifier, Adresse clientAdresse) throws UtilisateurException {
        if (clientAdresse.getRue() != null) {
            if (clientAdresse.getRue().isBlank())
                throw new UtilisateurException("la rue du client est absent");
            clientAModifier.getAdresse().setRue(clientAdresse.getRue());
        }
        if (clientAdresse.getCodePostal() != null) {
            if (clientAdresse.getCodePostal().isBlank())
                throw new UtilisateurException("le code postal du client est absent");
            clientAModifier.getAdresse().setCodePostal(clientAdresse.getCodePostal());
        }
        if (clientAdresse.getVille() != null) {
            if (clientAdresse.getVille().isBlank())
                throw new UtilisateurException("la ville du client est absent");
            clientAModifier.getAdresse().setVille(clientAdresse.getVille());
        }
    }

    private void verifierEtRamplacerEmailPassword(Client clientAModifier, String clientEmail, String clientPassword) throws UtilisateurException {
        if (clientEmail != null) {
            if (clientEmail.isBlank())
                throw new UtilisateurException("le mail du client est absent");
            if (!clientEmail.contains("@"))
                throw new UtilisateurException("le format de l'email du client est invalid");
            clientAModifier.setEmail(clientEmail);
        }
        if (clientPassword != null) {
            if (clientPassword.isBlank()) {
                throw new UtilisateurException("Le password du client est absent");
            }
            if (!passwordPattern.matcher(clientPassword).matches()) {
                throw new UtilisateurException("Le format du mot de passe du client est invalide");
            }
            clientAModifier.setPassword(passwordEncoder.encode(clientPassword));
        }
    }

    private static void verifierClientRequestDto(ClientRequestDto dto) throws UtilisateurException {
        if (dto == null)
            throw new UtilisateurException("le clientRequestDto est nulle");
        if (dto.nom() == null || dto.nom().isBlank())
            throw new UtilisateurException("le nom du client est absent");
        if (dto.prenom() == null || dto.prenom().isBlank())
            throw new UtilisateurException("le prénom du client est absent");
        verifierEmailPassword(dto);
        verifierAdresse(dto);
        if (dto.dateNaissance() == null)
            throw new UtilisateurException("la date de naissance du client est absent");
        int nouvelleAnnee = dto.dateNaissance().getYear() + 18;
        LocalDate nouvelleDate = LocalDate.of(nouvelleAnnee, dto.dateNaissance().getMonth(), dto.dateNaissance().getDayOfMonth());
        if (nouvelleDate.isAfter(LocalDate.now()))
            throw new UtilisateurException("pour vous inscrire sur notre site, il faut au moins 18 ans");

    }

    private static void verifierEmailPassword(ClientRequestDto dto) throws UtilisateurException {
        if (dto.email() == null || dto.email().isBlank())
            throw new UtilisateurException("le mail du client est absent");
        if (!dto.email().contains("@"))
            throw new UtilisateurException("le format de l'email du client est invalid");
        if (dto.password() == null || dto.password().isBlank())
            throw new UtilisateurException("le password du client est absent");
        if (!passwordPattern.matcher(dto.password()).matches())
            throw new UtilisateurException("le format du password du client est invalid");
    }

    private static void verifierAdresse(ClientRequestDto dto) throws UtilisateurException {
        if (dto.adresse() == null)
            throw new UtilisateurException("l'adresse du client est absent");
        if (dto.adresse().rue() == null || dto.adresse().rue().isBlank())
            throw new UtilisateurException("la rue du client est absent");
        if (dto.adresse().codePostal() == null || dto.adresse().codePostal().isBlank())
            throw new UtilisateurException("le code postal du client est absent");
        if (dto.adresse().ville() == null || dto.adresse().ville().isBlank())
            throw new UtilisateurException("la ville du client est absent");
    }


}




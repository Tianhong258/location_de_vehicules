package com.accenture.service;


import com.accenture.exception.UtilisateurException;
import com.accenture.repository.ClientDao;
import com.accenture.repository.entity.utilisateur.Adresse;
import com.accenture.repository.entity.utilisateur.Client;
import com.accenture.service.dto.utilisateur.ClientRequestDto;
import com.accenture.service.dto.utilisateur.ClientResponseDto;
import com.accenture.service.mapper.utilisateur.ClientMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;


@Service
public class ClientServiceImpl implements ClientService{

    private final ClientDao clientDao;
    private final ClientMapper clientMapper;
    private static final Pattern passwordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[&\\#@\\-_%§]).{6,}$");
    //private final PasswordEncoder passwordEncoder;


    public ClientServiceImpl(ClientDao clientDao, ClientMapper clientMapper) {
        this.clientDao = clientDao;
        this.clientMapper = clientMapper;
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
        Client clientEnreg = clientDao.save(client);
        return clientMapper.toClientResponseDto(clientEnreg);
    }

    /**
     * <p>La méthode <code>trouver</code> permet de récupérer un client avec son email et mot de passe.</p>
     *
     * @param email L'email du client.
     * @param password Le mot de passe du client.
     * @return Une réponse contenant les informations du client.
     * @throws EntityNotFoundException Si le client avec les informations données n'est pas trouvé.
     */
    @Override
    public ClientResponseDto trouver(String email, String password) throws EntityNotFoundException {
        Client client = verifierEmailPassword(email,password);
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
     * @param email L'email du client à désactiver ou supprimer.
     * @param password Le mot de passe du client à désactiver ou supprimer.
     * @throws UtilisateurException Si le client ne peut pas être désactivé ou supprimé.
     * @throws EntityNotFoundException Si le client n'est pas trouvé.
     */
    @Override
    public void desactiverOuSupprimer(String email, String password) throws UtilisateurException,EntityNotFoundException {
        Client client = verifierEmailPassword(email, password);
        clientDao.delete(client);
        //trouver les locations, s'il y a pas
        // l'utilisateur peut supprimer son compte : créer supprimer() et desactiver()
//        client.setDesactive(true);
//        clientDao.save(client);
        //clientDao.deleteByEmail(clientResponseDto.email());
    }


    /**
     * <p>La méthode <code>modifier</code> permet de modifier les informations d'un client.</p>
     *
     * @param email L'email du client à modifier.
     * @param password Le mot de passe du client à modifier.
     * @param clientRequestDto Les nouvelles informations du client.
     * @return Une réponse contenant les informations mises à jour du client.
     * @throws UtilisateurException Si les données du client sont invalides.
     * @throws EntityNotFoundException Si le client n'est pas trouvé.
     */
    @Override
    public ClientResponseDto modifier(String email, String password, ClientRequestDto clientRequestDto) throws UtilisateurException,EntityNotFoundException {
        Client client = verifierEmailPassword(email, password);
        Client clientModifie = clientMapper.toClient(clientRequestDto);
        clientModifie.setId(client.getId());
        clientDao.save(clientModifie);
        return clientMapper.toClientResponseDto(clientModifie);
    }

    /**
     * <p>La méthode <code>modifierPartiellement</code> permet de modifier partiellement les informations d'un client.</p>
     *
     * @param email L'email du client à modifier.
     * @param password Le mot de passe du client à modifier.
     * @param clientRequestDto Les nouvelles informations du client.
     * @return Une réponse contenant les informations mises à jour du client.
     * @throws UtilisateurException Si les données du client sont invalides.
     * @throws EntityNotFoundException Si le client n'est pas trouvé.
     */
    @Override
    public ClientResponseDto modifierPartiellement(String email, String password, ClientRequestDto clientRequestDto) throws UtilisateurException, EntityNotFoundException {
        Client clientAModifier = verifierEmailPassword(email, password);
        verifierClientRequestDto(clientRequestDto);
        Client nouveau = clientMapper.toClient(clientRequestDto);
        verifierEtRemplacer(nouveau, clientAModifier);
        Client clientEnreg = clientDao.save(clientAModifier);
        return clientMapper.toClientResponseDto(clientEnreg);
    }


    private Client verifierEmailPassword(String email, String password) throws EntityNotFoundException{
        Optional<Client> optClient = clientDao.findByEmailAndPassword(email, password);
        if(optClient.isEmpty())
            throw new EntityNotFoundException("Email n'existe pas ou password ne correspond pas");
        return optClient.get();
    }

    private static void verifierEtRemplacer(Client client, Client clientAModifier) throws UtilisateurException{
        if (client == null)
            throw new UtilisateurException("l'client est nulle");
        String clientNom = client.getNom();
        String clientPrenom = client.getPrenom();
        String clientEmail = client.getEmail();
        String clientPassword = client.getPassword();
        Adresse clientAdresse = client.getAdresse();
        LocalDate clientDateNaissance = client.getDateNaissance();
        if (clientNom != null && clientNom.isBlank())
            throw new UtilisateurException("le nom du client est absent");
        if(clientNom != null)
            clientAModifier.setNom(clientNom);
        if (clientPrenom != null && clientPrenom.isBlank())
            throw new UtilisateurException("le prénom du client est absent");
        if(clientPrenom != null)
            clientAModifier.setPrenom(clientPrenom);
        if (clientEmail != null && clientEmail.isBlank())
            throw new UtilisateurException("le mail du client est absent");
        if (clientEmail != null && !clientEmail.contains("@"))
            throw new UtilisateurException("le format de l'email du client est invalid");
        if(clientEmail != null)
            clientAModifier.setEmail(clientEmail);
        if (clientPassword != null && clientPassword.isBlank())
            throw new UtilisateurException("le password du client est absent");
        if(clientPassword != null && !passwordPattern.matcher(clientPassword).matches())
            throw new UtilisateurException("le format du password du client est invalid");
        if(clientPassword != null)
            clientAModifier.setPassword(clientPassword);
        if (clientAdresse.getRue() != null && clientAdresse.getRue().isBlank())
            throw new UtilisateurException("la rue du client est absent");
        if(clientAdresse.getRue() != null)
            clientAModifier.getAdresse().setRue(clientAdresse.getRue());
        if (clientAdresse.getCodePostal() != null && clientAdresse.getCodePostal().isBlank())
            throw new UtilisateurException("le code postal du client est absent");
        if(clientAdresse.getCodePostal() != null)
            clientAModifier.getAdresse().setCodePostal(clientAdresse.getCodePostal());
        if (clientAdresse.getVille() != null && clientAdresse.getVille().isBlank())
            throw new UtilisateurException("la ville du client est absent");
        if(clientAdresse.getVille() != null)
            clientAModifier.getAdresse().setVille(clientAdresse.getVille());
        if(clientDateNaissance != null) {
            int nouvelleAnnee = clientDateNaissance.getYear() + 18;
            LocalDate nouvelleDate = LocalDate.of(nouvelleAnnee, clientDateNaissance.getMonth(), clientDateNaissance.getDayOfMonth());
            if (nouvelleDate.isAfter(LocalDate.now()))
                throw new UtilisateurException("pour rester s'inscrire sur notre site, il faut au moins 18 ans");
            clientAModifier.setDateNaissance(clientDateNaissance);
        }

    }

    private static void verifierClientRequestDto(ClientRequestDto dto) throws UtilisateurException{
        //TODO: controller de permis ?????
        if (dto == null)
            throw new UtilisateurException("le clientRequestDto est nulle");
        if (dto.nom() == null || dto.nom().isBlank())
            throw new UtilisateurException("le nom du client est absent");
        if (dto.prenom() == null || dto.prenom().isBlank())
            throw new UtilisateurException("le prénom du client est absent");
        if (dto.email() == null || dto.email().isBlank())
            throw new UtilisateurException("le mail du client est absent");
        if( ! dto.email().contains("@"))
            throw new UtilisateurException("le format de l'email du client est invalid");
        if (dto.password() == null || dto.password().isBlank())
            throw new UtilisateurException("le password du client est absent");
        if(!passwordPattern.matcher(dto.password()).matches())
            throw new UtilisateurException("le format du password du client est invalid");
        if(dto.adresse() == null)
            throw new UtilisateurException("l'adresse du client est absent");
        if(dto.adresse().rue()== null || dto.adresse().rue().isBlank())
            throw new UtilisateurException("la rue du client est absent");
        if(dto.adresse().codePostal() == null || dto.adresse().codePostal().isBlank())
            throw new UtilisateurException("le code postal du client est absent");
        if(dto.adresse().ville() == null || dto.adresse().ville().isBlank())
            throw new UtilisateurException("la ville du client est absent");
        if (dto.dateNaissance() == null)
            throw new UtilisateurException("la date de naissance du client est absent");
        int nouvelleAnnee = dto.dateNaissance().getYear()+18;
        LocalDate nouvelleDate = LocalDate.of(nouvelleAnnee, dto.dateNaissance().getMonth(), dto.dateNaissance().getDayOfMonth());
        if(nouvelleDate.isAfter(LocalDate.now()))
            throw new UtilisateurException("pour vous inscrire sur notre site, il faut au moins 18 ans");


    }


}




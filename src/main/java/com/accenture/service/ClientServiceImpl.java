package com.accenture.service;


import com.accenture.exception.ClientException;
import com.accenture.repository.ClientDao;
import com.accenture.repository.entity.utilisateur.Client;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import com.accenture.service.mapper.ClientMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class ClientServiceImpl implements ClientService{

    private final ClientDao clientDao;
    private final ClientMapper clientMapper;
    //private final PasswordEncoder passwordEncoder;


    public ClientServiceImpl(ClientDao clientDao, ClientMapper clientMapper) {
        this.clientDao = clientDao;
        this.clientMapper = clientMapper;
    }

    @Override
    public ClientResponseDto ajouter(ClientRequestDto clientRequestDto) throws ClientException {
        verifierClient(clientRequestDto);
        Client client = clientMapper.toClient(clientRequestDto);
        Client clientEnreg = clientDao.save(client);
        return clientMapper.toClientResponseDto(clientEnreg);
    }

    @Override
    public ClientResponseDto trouver(String email, String password) throws EntityNotFoundException {
        Client client = verifierEmailPassword(email,password);
        return clientMapper.toClientResponseDto(client);
    }//TODO : refaire un peu test



    @Override
    public List<ClientResponseDto> trouverTous() {
        return clientDao.findAll()
                .stream()
                .map(clientMapper::toClientResponseDto)
                .toList();
    }

    @Override
    public void desactiverOuSupprimer(String email, String password) throws ClientException,EntityNotFoundException {
        Client client = verifierEmailPassword(email, password);
        //trouver les locations, s'il y a pas
        // l'utilisateur peut supprimer son compte : créer supprimer() et desactiver()
        client.setDesactive(true);
        clientDao.save(client);
        //clientDao.deleteByEmail(clientResponseDto.email());
    }

    @Override
    public ClientResponseDto modifier(String email, String password, ClientRequestDto clientRequestDto) throws ClientException,EntityNotFoundException {
        Client client = verifierEmailPassword(email, password);
        Client clientModifie = clientMapper.toClient(clientRequestDto);
        clientModifie.setId(client.getId());
        clientDao.save(clientModifie);
        return clientMapper.toClientResponseDto(clientModifie);
        //TODO : Il n'est pas content, je dois faire void pour vérifier la connection ?
    }


    private Client verifierEmailPassword(String email, String password) throws EntityNotFoundException{
        Optional<Client> optClient = clientDao.findByEmailAndPassword(email, password);
        if(optClient.isEmpty())
            throw new EntityNotFoundException("Email n'existe pas ou password ne correspond pas");
        return optClient.get();
    }

    private static void verifierClient(ClientRequestDto dto) throws ClientException{

        //TODO: controller de permis ?????
        if (dto == null)
            throw new ClientException("le clientRequestDto est nulle");
        if (dto.nom() == null || dto.nom().trim().isBlank())
            throw new ClientException("le nom du client est absent");
        if (dto.prenom() == null || dto.prenom().trim().isBlank())
            throw new ClientException("le prénom du client est absent");
        if (dto.email() == null || dto.email().trim().isBlank())
            throw new ClientException("le mail du client est absent");
        if (dto.password() == null || dto.password().trim().isBlank())
            throw new ClientException("le password du client est absent");
        if(dto.adresse() == null){
            throw new ClientException("l'adresse du client est absent");
        }
        if(dto.adresse().rue()== null || dto.adresse().rue().trim().isBlank()){
            throw new ClientException("la rue du client est absent");
        }
        if(dto.adresse().codePostal() == null || dto.adresse().codePostal().trim().isBlank()){
            throw new ClientException("le code postal du client est absent");
        }
        if(dto.adresse().ville() == null || dto.adresse().ville().trim().isBlank()){
            throw new ClientException("la ville du client est absent");
        }
        if (dto.dateNaissance() == null) {
            throw new ClientException("la date de naissance du client est absent");
        }
        int nouvelleAnnee = dto.dateNaissance().getYear()+18;
        LocalDate nouvelleDate = LocalDate.of(nouvelleAnnee, dto.dateNaissance().getMonth(), dto.dateNaissance().getDayOfMonth());
        if(nouvelleDate.isAfter(LocalDate.now())){
            throw new ClientException("pour vous inscrire sur notre site, il faut au moins 18 ans");
        }


    }


}




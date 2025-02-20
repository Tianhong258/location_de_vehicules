package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.repository.ClientDao;
import com.accenture.repository.entity.utilisateur.Client;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import com.accenture.service.mapper.ClientMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


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
    public ClientResponseDto ajouter(ClientRequestDto clientRequestDto) {
        verifierClient(clientRequestDto);
        Client client = clientMapper.toClient(clientRequestDto);
        Client clientEnreg = clientDao.save(client);
        return clientMapper.toClientResponseDto(clientEnreg);
    }

//    @Override
//    public List<ClientResponseDto> liste() {
//        return List.of();
//    }


    private static void verifierClient(ClientRequestDto dto) throws ClientException{

        //TODO; control de email, password, dateNaissance est bon ou pas
        //TODO: erreur toutes les situations à tester
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
        if(dto.adresse().rue() == null || dto.adresse().rue().trim().isBlank()){
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
        if(nouvelleDate.isBefore(LocalDate.now())){
            throw new ClientException("pour vous inscrire sur notre site, il faut au moins 18 ans");
        }
        if (dto.listePermis() == null || dto.listePermis().isEmpty())
            throw new ClientException("la liste de permis du client est absent");


    }


}




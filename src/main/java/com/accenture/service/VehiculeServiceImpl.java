package com.accenture.service;

import com.accenture.exception.UtilisateurException;
import com.accenture.exception.VehiculeException;
import com.accenture.model.CategorieVehicule;
import com.accenture.model.Filtre;
import com.accenture.repository.*;
import com.accenture.repository.entity.utilisateur.Client;
import com.accenture.repository.entity.vehicule.Moto;
import com.accenture.repository.entity.vehicule.Vehicule;
import com.accenture.repository.entity.vehicule.Voiture;
import com.accenture.service.dto.vehicule.*;
import com.accenture.service.mapper.vehicule.MotoMapper;
import com.accenture.service.mapper.vehicule.VoitureMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VehiculeServiceImpl implements VehiculeService{
    private final VehiculeDao vehiculeDao;
    private final VoitureMapper voitureMapper;
    private final MotoMapper motoMapper;
    private final ClientDao clientDao;

    public VehiculeServiceImpl(VehiculeDao vehiculeDao, VoitureMapper voitureMapper, MotoMapper motoMapper, ClientDao clientDao) {
        this.vehiculeDao = vehiculeDao;
        this.voitureMapper = voitureMapper;
        this.motoMapper = motoMapper;
        this.clientDao = clientDao;
    }
//    @Override
//    public VehiculeAdminDto trouverTous(){
//        List<Vehicule> listeVehicule = vehiculeDao.findAll();
//        List<VoitureResponseAdminDto> listeVoiture;
//        List<MotoResponseAdminDto> listeMoto = List.of();
//        distribuerVehicule(listeVehicule, listeVoiture, listeMoto);
//        return new VehiculeAdminDto(listeVoiture, listeMoto);
//    }



    @Override
    public VehiculeAdminDto filtrer(Filtre filtre) {
        List<VoitureResponseAdminDto> listeVoitures = new ArrayList<>();
        List<MotoResponseAdminDto> listeMotos = new ArrayList<>();
        List<Vehicule> listeVehicule;
        switch (filtre) {
            case ACTIF -> {
                listeVehicule = vehiculeDao.findByActifTrue();
                distribuerVehiculeEnAdminDto(listeVehicule,listeVoitures,listeMotos);
            }
            case NON_ACTIF -> {
                listeVehicule = vehiculeDao.findByActifFalse();
                distribuerVehiculeEnAdminDto(listeVehicule,listeVoitures,listeMotos);
            }
            case RETIRE -> {
                listeVehicule = vehiculeDao.findByRetireTrue();
                distribuerVehiculeEnAdminDto(listeVehicule,listeVoitures,listeMotos);
            }
            case NON_RETIRE -> {
                listeVehicule = vehiculeDao.findByRetireFalse();
                distribuerVehiculeEnAdminDto(listeVehicule,listeVoitures,listeMotos);
            }
        }
        return new VehiculeAdminDto(listeVoitures, listeMotos);
    }

    @Override
    public VehiculeClientDto rechercherParDate(LocalDate debut, LocalDate fin, String password, String email, CategorieVehicule categorie) throws UtilisateurException, EntityNotFoundException {
        long joursLocation = ChronoUnit.DAYS.between(debut, fin) + 1;
        double reduction = 1;
        VehiculeClientDto reponse;
        reduction = verifierConnectionCalculerTarif(password, email, reduction);
        List<Voiture> listeVoiture = new ArrayList<>();
        List<Moto> listeMoto = new ArrayList<>();
        List<Vehicule> listeVehicule = vehiculeDao.findByActifTrue();
        distribuerVehicule(listeVehicule, listeVoiture, listeMoto);
        List<MotoResponseClientDto> listeMotoClientDto = listeMotoResponseClientDtos(listeMoto,joursLocation, reduction);
        List<VoitureResponseClientDto> listeVoitureClientDto = listeVoitureResponseClientDtos(listeVoiture,joursLocation, reduction);
        if (categorie != null) {
            switch (categorie) {
                case CategorieVehicule.MOTO ->
                    reponse = new VehiculeClientDto(null, listeMotoClientDto);
                //TODO : ajouter filtrer type
                case CategorieVehicule.VOITURE ->
                    reponse = new VehiculeClientDto(listeVoitureClientDto, null);
                //TODO: ajouter filtrer type
                default ->
                    throw new VehiculeException("La catégorie est invalide");
            }
        }else reponse = new VehiculeClientDto(listeVoitureClientDto, listeMotoClientDto);
        return reponse;
}

    private double verifierConnectionCalculerTarif(String password, String email, double reduction) {
        if (password == null && email != null)
            throw new UtilisateurException("Password est obligatoire pour se connecter ! ");
        if (password != null && email == null)
            throw new UtilisateurException("Email est obligatoire pour se connecter !");
        if (email != null && password != null) {
            Optional<Client> optClient = clientDao.findByEmailAndPassword(email, password);
            if (optClient.isEmpty())
                throw new EntityNotFoundException("Email n'existe pas ou password ne correspond pas");
            else {
                reduction = 0.5; // TODO : corriger après location créer
            }
        }
        return reduction;
    }

    private void distribuerVehiculeEnAdminDto(List<Vehicule> listeVehicule, List<VoitureResponseAdminDto> listeVoiture, List<MotoResponseAdminDto> listeMoto) {
        for(Vehicule v : listeVehicule){
            if(v instanceof Voiture){
                listeVoiture.add(voitureMapper.toVoitureResponseAdminDto((Voiture) v));
            }
            if(v instanceof Moto){
                listeMoto.add(motoMapper.toMotoResponseAdminDto((Moto) v));
            }
        }
    }

    private void distribuerVehicule(List<Vehicule> listeVehicule, List<Voiture> listeVoiture, List<Moto> listeMoto) {
        for(Vehicule v : listeVehicule){
            if(v instanceof Voiture){
                listeVoiture.add((Voiture) v);
            }
            if(v instanceof Moto){
                listeMoto.add((Moto) v);
            }
        }
    }


    private List<MotoResponseClientDto> listeMotoResponseClientDtos(List<Moto> liste, long joursLocation, double reduction) {
    return liste.stream()
            .peek(v -> v.setTarif(Math.round(v.getTarif() * joursLocation * reduction * 10.0) / 10.0))
            .map(motoMapper::toMotoResponseClientDto)
            .sorted((m1, m2) -> (int) ((m1.tarif()) - m2.tarif()))
            .toList();
}


private List<VoitureResponseClientDto> listeVoitureResponseClientDtos(List<Voiture> liste, long joursLocation, double reduction) {
    return liste.stream()
            .peek(v -> v.setTarif(Math.round(v.getTarif() * joursLocation * reduction * 10.0) / 10.0))
            .map(voitureMapper::toVoitureResponseClientDto)
            .sorted((v1, v2) -> (int) ((v1.tarif()) - v2.tarif()))
            .toList();

}

}

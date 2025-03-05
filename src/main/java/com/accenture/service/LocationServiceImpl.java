package com.accenture.service;


import com.accenture.exception.LocationException;
import com.accenture.model.Etat;
import com.accenture.repository.ClientDao;
import com.accenture.repository.LocationDao;
import com.accenture.repository.VehiculeDao;
import com.accenture.repository.entity.location.Location;
import com.accenture.repository.entity.utilisateur.Client;
import com.accenture.repository.entity.vehicule.Vehicule;
import com.accenture.service.dto.location.LocationRequestDto;
import com.accenture.service.dto.location.LocationResponseDto;
import com.accenture.service.mapper.location.LocationMapper;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Service
public class LocationServiceImpl implements LocationService {
    private final LocationDao locationDao;
    private final ClientDao clientDao;
    private final LocationMapper locationMapper;
    private final VehiculeDao vehiculeDao;

    public LocationServiceImpl(LocationDao locationDao, ClientDao clientDao, LocationMapper locationMapper, VehiculeDao vehiculeDao) {
        this.locationDao = locationDao;
        this.clientDao = clientDao;
        this.locationMapper = locationMapper;
        this.vehiculeDao = vehiculeDao;
    }

    @Override
    public LocationResponseDto ajouter(Principal principal, LocationRequestDto locationRequestDto) throws LocationException {
            verifierLocationRequestDto(locationRequestDto);
            Location location = locationMapper.toLocation(locationRequestDto);
            Client client = clientDao.findByEmail(principal.getName()).orElseThrow();
            location.setClient(client);
            Vehicule vehicule = vehiculeDao.findById(locationRequestDto.vehiculeId()).orElseThrow();
            location.setVehicule(vehicule);
            double reduction = calculerReduction(client, location);
            location.setMontant(vehicule.getTarif()*reduction);
            location.setEtat(Etat.VALIDE);
            //contrôler le permis
            Location locationEnreg = locationDao.save(location);
            return locationMapper.toLocationResponseDto(locationEnreg);
    }


    @Override
    public List<LocationResponseDto> locations(){
        return locationDao.findAll().stream().map(locationMapper::toLocationResponseDto).toList();

    }

    private double calculerReduction(Client client, Location location) {
        double reduction = 1.0;
        int joursDejaFait = locationDao.findByClient(client)
                    .stream()
                    .filter(l->l.getEtat().equals(Etat.RESERVE))
                    .filter(l->l.getFin().isBefore(LocalDate.now()))
                    .map(l-> ((int) ChronoUnit.DAYS.between(l.getDebut(), l.getFin()) + 1))
                    .mapToInt(Integer::intValue)
                    .sum();
        int joursLocation = (int) ChronoUnit.DAYS.between(location.getDebut(), location.getFin()) + 1;
        if(joursLocation > 7)
            reduction *= 0.96;
        if(joursLocation > 14)
            reduction *= 0.92;
        if(joursDejaFait > 10 && joursDejaFait<= 30)
            reduction *= 0.95;
        if(joursDejaFait >30)
            reduction *= 0.90;
        return reduction;
    }


    private static void verifierLocationRequestDto(LocationRequestDto locationRequestDto){
        if(locationRequestDto.vehiculeId() <= 0 )
            throw new  LocationException("L'id de véhicule doit être un long positif");
        if(locationRequestDto.debut() == null)
            throw new  LocationException("La date de début de location est absente");
        if(locationRequestDto.debut().isBefore(LocalDate.now()))
            throw new  LocationException("La date de début de location doit être ajourd'hui ou après");
        if(locationRequestDto.fin() == null)
            throw new  LocationException("La date de fin de location est absente");
        if(locationRequestDto.fin().isBefore(LocalDate.now()))
            throw new LocationException("La date de fin de location doit être ajourd'hui ou après");
        if(locationRequestDto.kilometrage()<= 0)
            throw new LocationException("Le kilometrage doit être un entier positif");
    }

}

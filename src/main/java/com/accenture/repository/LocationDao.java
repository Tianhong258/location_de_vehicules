package com.accenture.repository;

import com.accenture.exception.VehiculeException;
import com.accenture.repository.entity.location.Location;
import com.accenture.repository.entity.utilisateur.Client;
import com.accenture.repository.entity.vehicule.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationDao extends JpaRepository<Location, Long> {
    List<Location> findByClient(Client client);
    List<Location> findByVehicule(Vehicule vehicule);
}

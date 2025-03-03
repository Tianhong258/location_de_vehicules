package com.accenture.repository;

import com.accenture.repository.entity.vehicule.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehiculeDao extends JpaRepository<Vehicule, Long> {

    List<Vehicule> findByActifTrue();
    List<Vehicule> findByActifFalse();
    List<Vehicule> findByRetireTrue();
    List<Vehicule> findByRetireFalse();
}

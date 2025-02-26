package com.accenture.repository;

import com.accenture.repository.entity.vehicule.Moto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MotoDao extends JpaRepository<Moto, Long> {
    List<Moto> findByOrderByActifDescRetireDesc();
    List<Moto> findByRetireTrue();
    List<Moto> findByRetireFalse();
    List<Moto> findByActifTrue();
    List<Moto> findByActifFalse();



}

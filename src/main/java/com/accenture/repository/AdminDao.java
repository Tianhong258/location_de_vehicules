package com.accenture.repository;

import com.accenture.repository.entity.utilisateur.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminDao extends JpaRepository<Admin, Long> {

    Optional<Admin> findByEmail(String email);

}

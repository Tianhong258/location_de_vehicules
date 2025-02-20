package com.accenture.repository;

import com.accenture.repository.entity.utilisateur.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminDao extends JpaRepository<Admin, Long> {
}

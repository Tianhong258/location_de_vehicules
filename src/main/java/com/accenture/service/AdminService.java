package com.accenture.service;

import com.accenture.exception.AdminException;
import com.accenture.service.dto.*;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface AdminService {
    AdminResponseDto ajouter(AdminRequestDto adminRequestDto) throws AdminException;

    AdminResponseDto trouver(String email, String password) throws AdminException, EntityNotFoundException;

    List<AdminResponseDto> trouverTous();

    void supprimer(String email, String password) throws EntityNotFoundException, AdminException;

    AdminResponseDto modifier(String email, String password, AdminRequestDto adminRequestDto) throws EntityNotFoundException, AdminException;

    AdminResponseDto modifierPartiellement(String email, String password, AdminRequestDto adminRequestDto) throws AdminException, EntityNotFoundException;

}

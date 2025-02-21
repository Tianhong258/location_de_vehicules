package com.accenture.service;

import com.accenture.service.dto.*;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface AdminService {
    AdminResponseDto ajouter(AdminRequestDto adminRequestDto);
    List<AdminResponseDto> liste();
    AdminResponseDto verifierEtTrouver(String email, String password) throws EntityNotFoundException;



}

package com.accenture.service;

import com.accenture.service.dto.*;

import java.util.List;

public interface AdminService {
    AdminResponseDto ajouter(AdminRequestDto adminRequestDto);
    List<AdminResponseDto> liste();



}

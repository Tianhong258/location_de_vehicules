package com.accenture.service.mapper.utilisateur;

import com.accenture.repository.entity.utilisateur.Admin;
import com.accenture.service.dto.utilisateur.AdminRequestDto;
import com.accenture.service.dto.utilisateur.AdminResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminMapper {
    Admin toAdmin(AdminRequestDto adminRequestDto);
    AdminResponseDto toAdminResponseDto (Admin admin);
}

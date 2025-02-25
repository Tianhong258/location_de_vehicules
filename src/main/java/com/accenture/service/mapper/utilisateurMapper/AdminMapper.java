package com.accenture.service.mapper.utilisateurMapper;

import com.accenture.repository.entity.utilisateur.Admin;
import com.accenture.service.dto.utilisateurDto.AdminRequestDto;
import com.accenture.service.dto.utilisateurDto.AdminResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminMapper {
    Admin toAdmin(AdminRequestDto adminRequestDto);
    AdminResponseDto toAdminResponseDto (Admin admin);
}

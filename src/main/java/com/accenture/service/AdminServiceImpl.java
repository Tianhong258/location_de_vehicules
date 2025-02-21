package com.accenture.service;

import com.accenture.exception.AdminException;
import com.accenture.repository.AdminDao;
import com.accenture.repository.entity.utilisateur.Admin;
import com.accenture.service.dto.AdminRequestDto;
import com.accenture.service.dto.AdminResponseDto;
import com.accenture.service.mapper.AdminMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService{

    private final AdminDao adminDao;
    private final AdminMapper adminMapper;
    //private final PasswordEncoder passwordEncoder;


    public AdminServiceImpl(AdminDao adminDao, AdminMapper adminMapper) {
        this.adminDao = adminDao;
        this.adminMapper = adminMapper;
    }


    @Override
    public AdminResponseDto ajouter(AdminRequestDto adminRequestDto) throws AdminException {
        verifierAdmin(adminRequestDto);
        Admin admin= adminMapper.toAdmin(adminRequestDto);
        Admin adminEnreg = adminDao.save(admin);
        return adminMapper.toAdminResponseDto(adminEnreg);
    }



    @Override
    public AdminResponseDto verifierEtTrouver(String email, String password) throws EntityNotFoundException {
        Optional<Admin> optAdmin = adminDao.findByEmail(email);
        AdminResponseDto adminResponseDto;
        if(optAdmin.isEmpty())
            throw new EntityNotFoundException("Impossible à trouver le mail");
        else if(optAdmin.get().getPassword().equals(password))
            adminResponseDto = adminMapper.toAdminResponseDto(optAdmin.get());
        else
            throw new EntityNotFoundException("Saisie de mot de passe est incorrecte");
        return adminResponseDto;
    }

    @Override
    public List<AdminResponseDto> liste() {
        return List.of();
    }

    private static void verifierAdmin(AdminRequestDto dto) throws AdminException {
        //TODO; control de email, password, dateNaissance est bon ou pas
        if (dto == null)
            throw new AdminException("l'adminRequestDto est nulle");
        if (dto.nom() == null || dto.nom().trim().isBlank())
            throw new AdminException("le nom de l'administrateur est absent");
        if (dto.prenom() == null || dto.prenom().trim().isBlank())
            throw new AdminException("le prénom de l'administrateur est absent");
        if (dto.email() == null || dto.email().trim().isBlank())
            throw new AdminException("le mail de l'administrateur est absent");
        if (dto.password() == null || dto.password().trim().isBlank())
            throw new AdminException("le password de l'administrateur est absent");
        if (dto.fonction() == null || dto.fonction().trim().isBlank())
            throw new AdminException("la fonction de l'administrateur est absente");
    }

}

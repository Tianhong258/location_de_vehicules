package com.accenture.service;

import com.accenture.exception.AdminException;
import com.accenture.repository.AdminDao;
import com.accenture.repository.entity.utilisateur.Admin;
import com.accenture.service.dto.utilisateurDto.AdminRequestDto;
import com.accenture.service.dto.utilisateurDto.AdminResponseDto;
import com.accenture.service.mapper.utilisateurMapper.AdminMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class AdminServiceImpl implements AdminService{

    private final AdminDao adminDao;
    private final AdminMapper adminMapper;
    private static final Pattern passwordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[&\\#@\\-_%§]).{6,}$");
    //private final PasswordEncoder passwordEncoder;


    public AdminServiceImpl(AdminDao adminDao, AdminMapper adminMapper) {
        this.adminDao = adminDao;
        this.adminMapper = adminMapper;
    }

    /**
     * <p> La méthode </p>
     * @param adminRequestDto
     * @return adminResponseDto
     * @throws AdminException
     */

    @Override
    public AdminResponseDto ajouter(AdminRequestDto adminRequestDto) throws AdminException {
        verifierAdminRequestDto(adminRequestDto);
        Admin admin= adminMapper.toAdmin(adminRequestDto);
        Admin adminEnreg = adminDao.save(admin);
        return adminMapper.toAdminResponseDto(adminEnreg);
    }

    @Override
    public AdminResponseDto trouver(String email, String password) throws AdminException, EntityNotFoundException {
        Admin admin = verifierEmailPassword(email, password);
        return adminMapper.toAdminResponseDto(admin);
    }

    @Override
    public List<AdminResponseDto> trouverTous() {
        return adminDao.findAll()
                .stream()
                .map(adminMapper::toAdminResponseDto)
                .toList();
    }

    @Override
    public void supprimer(String email, String password) throws EntityNotFoundException, AdminException {
        Admin admin = verifierEmailPassword(email, password);
        if(adminDao.count() > 1)
            adminDao.delete(admin);
        else
            throw new AdminException("Interdit de supprimer le compte du dernier administrateur ! ");
    }

    @Override
    public AdminResponseDto modifier(String email, String password, AdminRequestDto adminRequestDto ) throws EntityNotFoundException, AdminException {
        Admin adminAModifier = verifierEmailPassword(email, password);
        verifierAdminRequestDto(adminRequestDto);
        Admin nouveau = adminMapper.toAdmin(adminRequestDto);
        nouveau.setId(adminAModifier.getId());
        adminDao.save(nouveau);
        return adminMapper.toAdminResponseDto(nouveau);
    }

    @Override
    public AdminResponseDto modifierPartiellement(String email, String password, AdminRequestDto adminRequestDto) throws AdminException, EntityNotFoundException {
        Admin adminAModifier = verifierEmailPassword(email, password);
        Admin nouveau = adminMapper.toAdmin(adminRequestDto);
        verifierEtRemplacer(nouveau, adminAModifier);
        Admin adminEnreg = adminDao.save(adminAModifier);
        return adminMapper.toAdminResponseDto(adminEnreg);
    }


    private Admin verifierEmailPassword(String email, String password) throws AdminException, EntityNotFoundException{
        Optional<Admin> optAdmin = adminDao.findByEmailAndPassword(email, password);
        if(optAdmin.isEmpty())
            throw new EntityNotFoundException("Email n'existe pas ou password ne correspond pas");
        return optAdmin.get();
    }

    private static void verifierEtRemplacer(Admin admin, Admin adminAModifier) throws AdminException{
        if (admin == null)
            throw new AdminException("l'admin est null");
        String adminNom = admin.getNom();
        String adminPrenom = admin.getPrenom();
        String adminEmail = admin.getEmail();
        String adminPassword = admin.getPassword();
        String adminFonction = admin.getFonction();
        if (adminNom != null && adminNom.isBlank())
            throw new AdminException("le nom de l'administrateur est absent");
        if(adminNom != null)
            adminAModifier.setNom(adminNom);
        if (adminPrenom != null && adminPrenom.isBlank())
            throw new AdminException("le prénom de l'administrateur est absent");
        if(adminPrenom != null)
            adminAModifier.setPrenom(adminPrenom);
        if (adminEmail != null && adminEmail.isBlank())
            throw new AdminException("le mail de l'administrateur est absent");
        if (adminEmail != null && !adminEmail.contains("@"))
            throw new AdminException("le format de l'email de l'administrateur est invalide");
        if(adminEmail != null)
            adminAModifier.setEmail(adminEmail);
        if (adminPassword != null && adminPassword.isBlank())
            throw new AdminException("le password de l'administrateur est absent");
        if(adminPassword != null && !passwordPattern.matcher(adminPassword).matches())
            throw new AdminException("le format du password de l'administrateur est invalide");
        if(adminPassword != null)
               adminAModifier.setPassword(adminPassword);
        if (adminFonction != null && adminFonction.isBlank())
            throw new AdminException("la fonction de l'administrateur est absente");
        if(adminFonction != null)
            adminAModifier.setFonction(adminFonction);
    }


    private static void verifierAdminRequestDto(AdminRequestDto dto) throws AdminException {
        //TODO: dateNaissance est bon ou pas
        if (dto == null)
            throw new AdminException("l'adminRequestDto est null");
        if (dto.nom() == null || dto.nom().isBlank())
            throw new AdminException("le nom de l'administrateur est absent");
        if (dto.prenom() == null || dto.prenom().isBlank())
            throw new AdminException("le prénom de l'administrateur est absent");
        if (dto.email() == null || dto.email().isBlank())
            throw new AdminException("le mail de l'administrateur est absent");
        if( ! dto.email().contains("@"))
            throw new AdminException("le format de l'email de l'administrateur est invalide");
        if (dto.password() == null || dto.password().isBlank())
            throw new AdminException("le password de l'administrateur est absent");
        if(!passwordPattern.matcher(dto.password()).matches())
            throw new AdminException("le format du password de l'administrateur est invalide");
        if (dto.fonction() == null || dto.fonction().isBlank())
            throw new AdminException("la fonction de l'administrateur est absente");
    }

}


package com.accenture.service;

import com.accenture.exception.UtilisateurException;
import com.accenture.repository.AdminDao;
import com.accenture.repository.entity.utilisateur.Admin;
import com.accenture.service.dto.utilisateur.AdminRequestDto;
import com.accenture.service.dto.utilisateur.AdminResponseDto;
import com.accenture.service.mapper.utilisateur.AdminMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class AdminServiceImpl implements AdminService{

    private final AdminDao adminDao;
    private final AdminMapper adminMapper;
    private static final Pattern passwordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[&\\#@\\-_%§]).{6,}$");
    private final PasswordEncoder passwordEncoder;


    public AdminServiceImpl(AdminDao adminDao, AdminMapper adminMapper, PasswordEncoder passwordEncoder) {
        this.adminDao = adminDao;
        this.adminMapper = adminMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * <p>La méthode <code>ajouter</code> permet d'ajouter un nouvel administrateur.</p>
     *
     * @param adminRequestDto Les informations de l'administrateur à ajouter.
     * @return Une réponse contenant les informations de l'administrateur créé.
     * @throws UtilisateurException Si les données de l'administrateur sont invalides.
     */
    @Override
    public AdminResponseDto ajouter(AdminRequestDto adminRequestDto) throws UtilisateurException {
        verifierAdminRequestDto(adminRequestDto);
        Admin admin= adminMapper.toAdmin(adminRequestDto);
        String passwordChiffre = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(passwordChiffre);
        Admin adminEnreg = adminDao.save(admin);
        return adminMapper.toAdminResponseDto(adminEnreg);
    }


    /**
     * <p>La méthode <code>trouver</code> permet de récupérer un administrateur avec son email et mot de passe.</p>
     *
     * @param principal Les informations de l'administrateur connecté
     * @return Une réponse contenant les informations de l'administrateur.
     */
    @Override
    public AdminResponseDto trouver(Principal principal){
        Admin admin = adminDao.findByEmail(principal.getName()).orElseThrow();
        return adminMapper.toAdminResponseDto(admin);
    }


    /**
     * <p>La méthode <code>trouverTous</code> permet de récupérer tous les administrateurs.</p>
     *
     * @return Une liste contenant les informations de tous les administrateurs.
     */
    @Override
    public List<AdminResponseDto> trouverTous() {
        return adminDao.findAll()
                .stream()
                .map(adminMapper::toAdminResponseDto)
                .toList();
    }

    /**
     * <p>La méthode <code>supprimer</code> permet de supprimer un administrateur.</p>
     *
     * @param principal Les informations de l'administrateur connecté
     * @throws UtilisateurException Si la suppression du dernier administrateur est tentée.
     */
    @Override
    public void supprimer(Principal principal) throws UtilisateurException {
        Admin admin = adminDao.findByEmail(principal.getName()).orElseThrow();
        if(adminDao.count() > 1)
            adminDao.delete(admin);
        else
            throw new UtilisateurException("Interdit de supprimer le compte du dernier administrateur ! ");
    }

    /**
     * <p>La méthode <code>modifierPartiellement</code> permet de modifier partiellement les informations d'un administrateur.</p>
     *
     * @param principal Les informations de l'administrateur connecté
     * @param adminRequestDto Les nouvelles informations de l'administrateur.
     * @return Une réponse contenant les informations mises à jour de l'administrateur.
     * @throws UtilisateurException Si les données de l'administrateur sont invalides.
     */

    @Override
    public AdminResponseDto modifierPartiellement(Principal principal, AdminRequestDto adminRequestDto) throws UtilisateurException{
        Admin adminAModifier = adminDao.findByEmail(principal.getName()).orElseThrow();
        Admin nouveau = adminMapper.toAdmin(adminRequestDto);
        verifierEtRemplacer(nouveau, adminAModifier);
        Admin adminEnreg = adminDao.save(adminAModifier);
        return adminMapper.toAdminResponseDto(adminEnreg);
    }


    private void verifierEtRemplacer(Admin admin, Admin adminAModifier) throws UtilisateurException{
        if (admin == null)
            throw new UtilisateurException("l'admin est null");
        String adminNom = admin.getNom();
        String adminPrenom = admin.getPrenom();
        String adminEmail = admin.getEmail();
        String adminPassword = admin.getPassword();
        String adminFonction = admin.getFonction();
        if (adminNom != null){
            if(adminNom.isBlank())
                throw new UtilisateurException("le nom de l'administrateur est absent");
            adminAModifier.setNom(adminNom);
        }
        if (adminPrenom != null){
            if(adminPrenom.isBlank())
                throw new UtilisateurException("le prénom de l'administrateur est absent");
            adminAModifier.setPrenom(adminPrenom);
        }
        verifierEtRemplacerEmailPassword(adminAModifier, adminEmail, adminPassword);
        if (adminFonction != null){
            if(adminFonction.isBlank())
                throw new UtilisateurException("la fonction de l'administrateur est absente");
            adminAModifier.setFonction(adminFonction);
        }
    }

    private void verifierEtRemplacerEmailPassword(Admin adminAModifier, String adminEmail, String adminPassword) throws UtilisateurException{
        if (adminEmail != null){
            if(adminEmail.isBlank())
                throw new UtilisateurException("le mail de l'administrateur est absent");
            if(!adminEmail.contains("@"))
                throw new UtilisateurException("le format de l'email de l'administrateur est invalide");
            adminAModifier.setEmail(adminEmail);
        }
        if (adminPassword != null) {
            if (adminPassword.isBlank())
                throw new UtilisateurException("le password de l'administrateur est absent");
            if(!passwordPattern.matcher(adminPassword).matches())
                throw new UtilisateurException("le format du password de l'administrateur est invalide");
            adminAModifier.setPassword(passwordEncoder.encode(adminPassword));
        }
    }


    private static void verifierAdminRequestDto(AdminRequestDto dto) throws UtilisateurException {
        //TODO: dateNaissance est bon ou pas
        if (dto == null)
            throw new UtilisateurException("l'adminRequestDto est null");
        if (dto.nom() == null || dto.nom().isBlank())
            throw new UtilisateurException("le nom de l'administrateur est absent");
        if (dto.prenom() == null || dto.prenom().isBlank())
            throw new UtilisateurException("le prénom de l'administrateur est absent");
        verifierEmailPassword(dto);
        if (dto.fonction() == null || dto.fonction().isBlank())
            throw new UtilisateurException("la fonction de l'administrateur est absente");
    }

    private static void verifierEmailPassword(AdminRequestDto dto) throws UtilisateurException{
        if (dto.email() == null || dto.email().isBlank())
            throw new UtilisateurException("le mail de l'administrateur est absent");
        if (! dto.email().contains("@"))
            throw new UtilisateurException("le format de l'email de l'administrateur est invalide");
        if (dto.password() == null || dto.password().isBlank())
            throw new UtilisateurException("le password de l'administrateur est absent");
        if(!passwordPattern.matcher(dto.password()).matches())
            throw new UtilisateurException("le format du password de l'administrateur est invalide");
    }

}


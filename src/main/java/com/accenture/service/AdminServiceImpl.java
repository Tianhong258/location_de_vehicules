package com.accenture.service;

import com.accenture.exception.UtilisateurException;
import com.accenture.repository.AdminDao;
import com.accenture.repository.entity.utilisateur.Admin;
import com.accenture.service.dto.utilisateur.AdminRequestDto;
import com.accenture.service.dto.utilisateur.AdminResponseDto;
import com.accenture.service.mapper.utilisateur.AdminMapper;
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
        Admin adminEnreg = adminDao.save(admin);
        return adminMapper.toAdminResponseDto(adminEnreg);
    }


    /**
     * <p>La méthode <code>trouver</code> permet de récupérer un administrateur avec son email et mot de passe.</p>
     *
     * @param email L'email de l'administrateur.
     * @param password Le mot de passe de l'administrateur.
     * @return Une réponse contenant les informations de l'administrateur.
     * @throws UtilisateurException Si les informations de l'administrateur sont invalides.
     * @throws EntityNotFoundException Si l'administrateur n'est pas trouvé.
     */
    @Override
    public AdminResponseDto trouver(String email, String password) throws UtilisateurException, EntityNotFoundException {
        Admin admin = verifierEmailPassword(email, password);
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
     * @param email L'email de l'administrateur à supprimer.
     * @param password Le mot de passe de l'administrateur à supprimer.
     * @throws UtilisateurException Si la suppression du dernier administrateur est tentée.
     * @throws EntityNotFoundException Si l'administrateur n'est pas trouvé.
     */
    @Override
    public void supprimer(String email, String password) throws EntityNotFoundException, UtilisateurException {
        Admin admin = verifierEmailPassword(email, password);
        if(adminDao.count() > 1)
            adminDao.delete(admin);
        else
            throw new UtilisateurException("Interdit de supprimer le compte du dernier administrateur ! ");
    }

    /**
     * <p>La méthode <code>modifier</code> permet de modifier les informations d'un administrateur.</p>
     *
     * @param email L'email de l'administrateur à modifier.
     * @param password Le mot de passe de l'administrateur à modifier.
     * @param adminRequestDto Les nouvelles informations de l'administrateur.
     * @return Une réponse contenant les informations mises à jour de l'administrateur.
     * @throws EntityNotFoundException Si l'administrateur n'est pas trouvé.
     * @throws UtilisateurException Si les données de l'administrateur sont invalides.
     */
    @Override
    public AdminResponseDto modifier(String email, String password, AdminRequestDto adminRequestDto ) throws EntityNotFoundException, UtilisateurException {
        Admin adminAModifier = verifierEmailPassword(email, password);
        verifierAdminRequestDto(adminRequestDto);
        Admin nouveau = adminMapper.toAdmin(adminRequestDto);
        nouveau.setId(adminAModifier.getId());
        adminDao.save(nouveau);
        return adminMapper.toAdminResponseDto(nouveau);
    }

    /**
     * <p>La méthode <code>modifierPartiellement</code> permet de modifier partiellement les informations d'un administrateur.</p>
     *
     * @param email L'email de l'administrateur à modifier.
     * @param password Le mot de passe de l'administrateur à modifier.
     * @param adminRequestDto Les nouvelles informations de l'administrateur.
     * @return Une réponse contenant les informations mises à jour de l'administrateur.
     * @throws UtilisateurException Si les données de l'administrateur sont invalides.
     * @throws EntityNotFoundException Si l'administrateur n'est pas trouvé.
     */

    @Override
    public AdminResponseDto modifierPartiellement(String email, String password, AdminRequestDto adminRequestDto) throws UtilisateurException, EntityNotFoundException {
        Admin adminAModifier = verifierEmailPassword(email, password);
        Admin nouveau = adminMapper.toAdmin(adminRequestDto);
        verifierEtRemplacer(nouveau, adminAModifier);
        Admin adminEnreg = adminDao.save(adminAModifier);
        return adminMapper.toAdminResponseDto(adminEnreg);
    }


    private Admin verifierEmailPassword(String email, String password) throws UtilisateurException, EntityNotFoundException{
        Optional<Admin> optAdmin = adminDao.findByEmailAndPassword(email, password);
        if(optAdmin.isEmpty())
            throw new EntityNotFoundException("Email n'existe pas ou password ne correspond pas");
        return optAdmin.get();
    }

    private static void verifierEtRemplacer(Admin admin, Admin adminAModifier) throws UtilisateurException{
        if (admin == null)
            throw new UtilisateurException("l'admin est null");
        String adminNom = admin.getNom();
        String adminPrenom = admin.getPrenom();
        String adminEmail = admin.getEmail();
        String adminPassword = admin.getPassword();
        String adminFonction = admin.getFonction();
        if (adminNom != null && adminNom.isBlank())
            throw new UtilisateurException("le nom de l'administrateur est absent");
        if(adminNom != null)
            adminAModifier.setNom(adminNom);
        if (adminPrenom != null && adminPrenom.isBlank())
            throw new UtilisateurException("le prénom de l'administrateur est absent");
        if(adminPrenom != null)
            adminAModifier.setPrenom(adminPrenom);
        if (adminEmail != null && adminEmail.isBlank())
            throw new UtilisateurException("le mail de l'administrateur est absent");
        if (adminEmail != null && !adminEmail.contains("@"))
            throw new UtilisateurException("le format de l'email de l'administrateur est invalide");
        if(adminEmail != null)
            adminAModifier.setEmail(adminEmail);
        if (adminPassword != null && adminPassword.isBlank())
            throw new UtilisateurException("le password de l'administrateur est absent");
        if(adminPassword != null && !passwordPattern.matcher(adminPassword).matches())
            throw new UtilisateurException("le format du password de l'administrateur est invalide");
        if(adminPassword != null)
               adminAModifier.setPassword(adminPassword);
        if (adminFonction != null && adminFonction.isBlank())
            throw new UtilisateurException("la fonction de l'administrateur est absente");
        if(adminFonction != null)
            adminAModifier.setFonction(adminFonction);
    }


    private static void verifierAdminRequestDto(AdminRequestDto dto) throws UtilisateurException {
        //TODO: dateNaissance est bon ou pas
        if (dto == null)
            throw new UtilisateurException("l'adminRequestDto est null");
        if (dto.nom() == null || dto.nom().isBlank())
            throw new UtilisateurException("le nom de l'administrateur est absent");
        if (dto.prenom() == null || dto.prenom().isBlank())
            throw new UtilisateurException("le prénom de l'administrateur est absent");
        if (dto.email() == null || dto.email().isBlank())
            throw new UtilisateurException("le mail de l'administrateur est absent");
        if( ! dto.email().contains("@"))
            throw new UtilisateurException("le format de l'email de l'administrateur est invalide");
        if (dto.password() == null || dto.password().isBlank())
            throw new UtilisateurException("le password de l'administrateur est absent");
        if(!passwordPattern.matcher(dto.password()).matches())
            throw new UtilisateurException("le format du password de l'administrateur est invalide");
        if (dto.fonction() == null || dto.fonction().isBlank())
            throw new UtilisateurException("la fonction de l'administrateur est absente");
    }

}


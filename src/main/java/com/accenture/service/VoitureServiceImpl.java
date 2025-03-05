package com.accenture.service;

import com.accenture.exception.VehiculeException;
import com.accenture.model.Filtre;
import com.accenture.model.Permis;
import com.accenture.repository.VoitureDao;
import com.accenture.repository.entity.vehicule.Voiture;
import com.accenture.service.dto.vehicule.VoitureRequestDto;
import com.accenture.service.dto.vehicule.VoitureResponseAdminDto;
import com.accenture.service.mapper.vehicule.VoitureMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VoitureServiceImpl implements VoitureService {
    private final VoitureDao voitureDao;
    private final VoitureMapper voitureMapper;
    public static final String ID_NON_PRESENT = "POM POM POM, id non présent";

    public VoitureServiceImpl(VoitureDao voitureDao, VoitureMapper voitureMapper) {
        this.voitureDao = voitureDao;
        this.voitureMapper = voitureMapper;
    }

    /**
     * <p>La méthode <code>ajouter</code> permet d'ajouter une nouvelle voiture en validant ses informations.</p>
     *
     * @param voitureRequestDto Les informations de la voiture à ajouter.
     * @return Une réponse contenant les informations de la voiture ajoutée.
     * @throws VehiculeException Si les informations de la voiture sont invalides.
     */
    @Override
    public VoitureResponseAdminDto ajouter(VoitureRequestDto voitureRequestDto) throws VehiculeException {
        verifierVoiture(voitureRequestDto);
        Voiture voiture = voitureMapper.toVoiture(voitureRequestDto);
        genererPermisVoiture(voiture);
        Voiture voitureEnreg = voitureDao.save(voiture);
        return voitureMapper.toVoitureResponseAdminDto(voitureEnreg);
    }

    /**
     * <p>La méthode <code>trouverToutes</code> permet de récupérer toutes les voitures, triées par leur statut actif et retiré.</p>
     *
     * @return Une liste de réponses contenant les informations de toutes les voitures.
     */
    @Override
    public List<VoitureResponseAdminDto> trouverToutes() {
        return voitureDao.findByOrderByActifDescRetireDesc()
                .stream()
                .map(voitureMapper::toVoitureResponseAdminDto)
                .toList();
    }

    /**
     * <p>La méthode <code>filtrer</code> permet de filtrer les voitures selon leur statut actif ou retiré.</p>
     *
     * @param filtre Le filtre à appliquer pour récupérer les voitures (actif, non actif, retiré, non retiré).
     * @return Une liste de réponses contenant les informations des voitures correspondant au filtre.
     */
    @Override
    public List<VoitureResponseAdminDto> filtrer(Filtre filtre) {
        List<Voiture> liste = switch (filtre) {
            case ACTIF -> voitureDao.findByActifTrue();
            case NON_ACTIF -> voitureDao.findByActifFalse();
            case RETIRE -> voitureDao.findByRetireTrue();
            case NON_RETIRE -> voitureDao.findByRetireFalse();
        };
        return liste.stream()
                .map(voitureMapper::toVoitureResponseAdminDto)
                .toList();
    }


    /**
     * <p>La méthode <code>trouver</code> permet de récupérer une voiture en fonction de son id.</p>
     *
     * @param id L'id de la voiture à récupérer.
     * @return Une réponse contenant les informations de la voiture trouvée.
     * @throws EntityNotFoundException Si la voiture avec l'id spécifié n'est pas trouvée.
     */
    @Override
    public VoitureResponseAdminDto trouver(long id) throws EntityNotFoundException {
        Optional<Voiture> optVoiture = voitureDao.findById(id);
        if (optVoiture.isEmpty())
            throw new EntityNotFoundException(ID_NON_PRESENT);
        Voiture voiture = optVoiture.get();
        return voitureMapper.toVoitureResponseAdminDto(voiture);
    }

    /**
     * <p>La méthode <code>supprimer</code> permet de supprimer une voiture en fonction de son id.</p>
     *
     * @param id L'id de la voiture à supprimer.
     * @throws EntityNotFoundException Si la voiture avec l'id spécifié n'est pas trouvée.
     */
    @Override
    public void supprimer(long id) throws EntityNotFoundException {
        if (voitureDao.existsById(id))
            voitureDao.deleteById(id);
        else
            throw new EntityNotFoundException(ID_NON_PRESENT);
        //TODO: si y a pas de location, fait ceci, sinon, mettre le retire en true
    }
    /**
     * <p>La méthode <code>modifier</code> permet de modifier les informations d'une voiture existante.</p>
     *
     * @param id L'id de la voiture à modifier.
     * @param voitureRequestDto Les nouvelles informations de la voiture.
     * @return Une réponse contenant les informations mises à jour de la voiture.
     * @throws VehiculeException Si les informations de la voiture sont invalides ou si la voiture est déjà retirée.
     * @throws EntityNotFoundException Si la voiture avec l'id spécifié n'est pas trouvée.
     */
    @Override
    public VoitureResponseAdminDto modifier(long id, VoitureRequestDto voitureRequestDto) throws VehiculeException, EntityNotFoundException {
        Optional<Voiture> optVoiture = voitureDao.findById(id);
        if (optVoiture.isEmpty())
            throw new EntityNotFoundException(ID_NON_PRESENT);
        Voiture voitureExistante = optVoiture.get();
        if(voitureExistante.getRetire())
            throw new VehiculeException("Impossible de modifier une voiture retirée depuis le parc");
        Voiture nouvelle = voitureMapper.toVoiture(voitureRequestDto);
        verifierEtRemplacer(nouvelle, voitureExistante);
        Voiture voitureEnreg = voitureDao.save(voitureExistante);
        return voitureMapper.toVoitureResponseAdminDto(voitureEnreg);
    }


    private static void genererPermisVoiture(Voiture voiture) {
        if (voiture.getNombrePlaces() > 9 && voiture.getNombrePlaces() <=16 )
            voiture.setPermis(List.of(Permis.D1));
        if (voiture.getNombrePlaces() > 0 && voiture.getNombrePlaces() <= 9)
            voiture.setPermis(List.of(Permis.B));
    }

    private static void verifierEtRemplacer(Voiture nouvelle, Voiture voitureExiste) {
        if (nouvelle == null)
            throw new VehiculeException("la nouvelle voiture est null");
        verifierRemplacerMarqueModele(nouvelle, voitureExiste);
        verifierRemplacerCouleurNomPlaces(nouvelle, voitureExiste);
        verifierRemplacerPortesCarburantTrans(nouvelle, voitureExiste);
        verifierRemplacerClimaType(nouvelle, voitureExiste);
        verifierRemplacerBagagesTarifKilo(nouvelle, voitureExiste);
        verifierRemplacerActifRetire(nouvelle, voitureExiste);
        //TODO : vérifier Permis ou pas
    }


    private static void verifierVoiture(VoitureRequestDto dto) throws VehiculeException {
        if (dto == null)
            throw new VehiculeException("le voitureRequestDto est null");
        verifierMarqueModeleCouleur(dto);
        verifierPlacesPortesCarburant(dto);
        verifierTransClimaBagages(dto);
        verifierTypeTarifKilo(dto);
        verifierActifRetire(dto);
        //TODO : vérifier Permis ou pas, pourquoi les Integer ne peuvent pas être null ?
    }

    private static void verifierRemplacerActifRetire(Voiture nouvelle, Voiture voitureExiste) {
        if (nouvelle.getActif() != null)
            voitureExiste.setActif(nouvelle.getActif());
        if (nouvelle.getRetire() != null)
            voitureExiste.setRetire(nouvelle.getRetire());
        if(voitureExiste.getActif() && voitureExiste.getRetire())
            throw new VehiculeException("la voiture qui est retirée depuis le parc ne peut pas être activée !");
    }

    private static void verifierRemplacerClimaType(Voiture nouvelle, Voiture voitureExiste) {
        if (nouvelle.getClimatisation() != null)
            voitureExiste.setClimatisation(nouvelle.getClimatisation());
        if (nouvelle.getTypeVoiture() != null)
            voitureExiste.setTypeVoiture(nouvelle.getTypeVoiture());
    }

    private static void verifierRemplacerPortesCarburantTrans(Voiture nouvelle, Voiture voitureExiste) {
        if (nouvelle.getNombrePortes() != null) {
            voitureExiste.setNombrePortes(nouvelle.getNombrePortes());
        }
        if (nouvelle.getTypeCarburant() != null)
            voitureExiste.setTypeCarburant(nouvelle.getTypeCarburant());
        if (nouvelle.getTransmission() != null)
            voitureExiste.setTransmission(nouvelle.getTransmission());
    }


    private static void verifierRemplacerMarqueModele(Voiture nouvelle, Voiture voitureExiste) {
        if (nouvelle.getMarque() != null) {
            if (nouvelle.getMarque().isBlank())
                throw new VehiculeException("la marque de la voiture est absente");
            voitureExiste.setMarque(nouvelle.getMarque());
        }
        if (nouvelle.getModele() != null) {
            if (nouvelle.getModele().isBlank())
                throw new VehiculeException("le modèle de la voiture est absent");
            voitureExiste.setModele(nouvelle.getModele());
        }
    }

    private static void verifierRemplacerCouleurNomPlaces(Voiture nouvelle, Voiture voitureExiste) {
        if (nouvelle.getCouleur() != null) {
            if (nouvelle.getCouleur().isBlank())
                throw new VehiculeException("la couleur de la voiture est absente");
            voitureExiste.setCouleur(nouvelle.getCouleur());
        }
        if (nouvelle.getNombrePlaces() != null) {
            if (nouvelle.getNombrePlaces() <= 0)
                throw new VehiculeException("le nombre de places est absent ou il est négatif");
            voitureExiste.setNombrePlaces(nouvelle.getNombrePlaces());
        }
    }

    private static void verifierRemplacerBagagesTarifKilo(Voiture nouvelle, Voiture voitureExiste) {
        if (nouvelle.getNombreBagages() != null) {
            if (nouvelle.getNombreBagages() <= 0)
                throw new VehiculeException("le nombre de bagages est absent ou il est négatif");
            voitureExiste.setNombreBagages(nouvelle.getNombreBagages());
        }

        if (nouvelle.getTarif() != null) {
            if (nouvelle.getTarif() <= 0)
                throw new VehiculeException("le tarif par jour est absent ou il est négatif");
            voitureExiste.setTarif(nouvelle.getTarif());
        }
        if (nouvelle.getKilometrage() != null) {
            if (nouvelle.getKilometrage() <= 0)
                throw new VehiculeException("le kilometrage est absent ou il est négatif");
            voitureExiste.setKilometrage(nouvelle.getKilometrage());
        }
    }


    private static void verifierActifRetire(VoitureRequestDto dto) {
        if (dto.actif() == null)
            throw new VehiculeException("l'actif est absent");
        if (dto.retire() == null)
            throw new VehiculeException("le retire est absent");
        if(dto.retire() && dto.actif())
            throw new VehiculeException("la voiture qui est retirée depuis le parc ne peut pas être activée !");
    }

    private static void verifierTypeTarifKilo(VoitureRequestDto dto) {
        if (dto.type() == null)
            throw new VehiculeException("le type de la voiture est absent");
        if (dto.tarif() == null || dto.tarif() < 0)
            throw new VehiculeException("le tarif par jour est absent ou il est négatif");
        if (dto.kilometrage() == null || dto.kilometrage() < 0)
            throw new VehiculeException("le kilometrage est absent ou il est négatif");
    }

    private static void verifierTransClimaBagages(VoitureRequestDto dto) {
        if (dto.transmission() == null)
            throw new VehiculeException("la transmission de la voiture est absente");
        if (dto.climatisation() == null)
            throw new VehiculeException("la climatisation est absent");
        if (dto.nombreBagages() == null || dto.nombreBagages() < 0)
            throw new VehiculeException("le nombre de bagages est absent ou il est négatif");
    }

    private static void verifierPlacesPortesCarburant(VoitureRequestDto dto) {
        if (dto.nombrePlaces() == null || dto.nombrePlaces() <= 0)
            throw new VehiculeException("le nombre de places est absent ou il est négatif");
        if (dto.nombrePortes() == null)
            throw new VehiculeException("le nombre de portes est absent");
        if (dto.typeCarburant() == null)
            throw new VehiculeException("le type de carburant de la voiture est absent");
    }

    private static void verifierMarqueModeleCouleur(VoitureRequestDto dto) {
        if (dto.marque() == null || dto.marque().isBlank())
            throw new VehiculeException("la marque de la voiture est absente");
        if (dto.modele() == null || dto.modele().isBlank())
            throw new VehiculeException("le modèle de la voiture est absent");
        if (dto.couleur() == null || dto.couleur().isBlank())
            throw new VehiculeException("la couleur de la voiture est absente");
    }

}

package com.accenture.service;

import com.accenture.exception.VoitureException;
import com.accenture.model.Filtre;
import com.accenture.model.Permis;
import com.accenture.repository.VoitureDao;
import com.accenture.repository.entity.vehicule.Voiture;
import com.accenture.service.dto.vehiculeDto.VoitureRequestDto;
import com.accenture.service.dto.vehiculeDto.VoitureResponseAdminDto;
import com.accenture.service.mapper.vehiculeMapper.VoitureMapper;
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

    @Override
    public VoitureResponseAdminDto ajouter(VoitureRequestDto voitureRequestDto) throws VoitureException {
        verifierVoiture(voitureRequestDto);
        Voiture voiture = voitureMapper.toVoiture(voitureRequestDto);
        if (voiture.getNombrePlaces() > 9)
            voiture.setPermis(Permis.D1);
        else
            voiture.setPermis(Permis.B);
        Voiture voitureEnreg = voitureDao.save(voiture);
        return voitureMapper.toVoitureResponseAdminDto(voitureEnreg);
    }

    @Override
    public List<VoitureResponseAdminDto> trouverToutes() {
        return voitureDao.findByOrderByActifDescRetireDesc()
                .stream()
                .map(voitureMapper::toVoitureResponseAdminDto)
                .toList();
    }

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



    @Override
    public VoitureResponseAdminDto trouver(long id) throws EntityNotFoundException {
        Optional<Voiture> optVoiture = voitureDao.findById(id);
        if (optVoiture.isEmpty())
            throw new EntityNotFoundException(ID_NON_PRESENT);
        Voiture voiture = optVoiture.get();
        return voitureMapper.toVoitureResponseAdminDto(voiture);
    }

    @Override
    public void supprimer(long id) throws EntityNotFoundException {
        if (voitureDao.existsById(id))
            voitureDao.deleteById(id);
        else
            throw new EntityNotFoundException(ID_NON_PRESENT);
        //TODO: si y a pas de location, fait ceci, sinon, mettre le retire en true
    }

    @Override
    public VoitureResponseAdminDto modifier(long id, VoitureRequestDto voitureRequestDto) throws VoitureException, EntityNotFoundException {
        Optional<Voiture> optVoiture = voitureDao.findById(id);
        if (optVoiture.isEmpty())
            throw new EntityNotFoundException(ID_NON_PRESENT);
        Voiture voitureExistante = optVoiture.get();
        Voiture nouvelle = voitureMapper.toVoiture(voitureRequestDto);
        verifierEtRemplacer(nouvelle, voitureExistante);
        Voiture voitureEnreg = voitureDao.save(voitureExistante);
        return voitureMapper.toVoitureResponseAdminDto(voitureEnreg);
    }

    private static void verifierEtRemplacer(Voiture nouvelle, Voiture voitureExiste) {
        if (nouvelle == null)
            throw new VoitureException("la nouvelle voiture est null");
        if (nouvelle.getMarque() != null) {
            if (nouvelle.getMarque().isBlank())
                throw new VoitureException("la marque de la voiture est absente");
            voitureExiste.setMarque(nouvelle.getMarque());
        }
        if (nouvelle.getModele() != null) {
            if (nouvelle.getModele().isBlank())
                throw new VoitureException("le modèle de la voiture est absent");
            voitureExiste.setModele(nouvelle.getModele());
        }
        if (nouvelle.getCouleur() != null) {
            if (nouvelle.getCouleur().isBlank())
                throw new VoitureException("la couleur de la voiture est absente");
            voitureExiste.setCouleur(nouvelle.getCouleur());
        }
        if (nouvelle.getNombrePlaces() != null) {
            if (nouvelle.getNombrePlaces() <= 0)
                throw new VoitureException("le nombre de places est absent ou il est négatif");
            voitureExiste.setNombrePlaces(nouvelle.getNombrePlaces());
        }
        if (nouvelle.getNombrePortes() != null) {
            voitureExiste.setNombrePortes(nouvelle.getNombrePortes());
        }
        if (nouvelle.getTypeCarburant() != null)
            voitureExiste.setTypeCarburant(nouvelle.getTypeCarburant());
        if (nouvelle.getTransmission() != null)
            voitureExiste.setTransmission(nouvelle.getTransmission());
        if (nouvelle.getClimatisation() != null)
            voitureExiste.setClimatisation(nouvelle.getClimatisation());

        if (nouvelle.getNombreBagages() != null) {
            if (nouvelle.getNombreBagages() <= 0)
                throw new VoitureException("le nombre de bagages est absent ou il est négatif");
            voitureExiste.setNombreBagages(nouvelle.getNombreBagages());
        }
        if (nouvelle.getType() != null)
            voitureExiste.setType(nouvelle.getType());
        if (nouvelle.getTarifParJour() != null) {
            if (nouvelle.getTarifParJour() <= 0)
                throw new VoitureException("le tarif par jour est absent ou il est négatif");
            voitureExiste.setTarifParJour(nouvelle.getTarifParJour());
        }
        if (nouvelle.getKilometrage() != null) {
            if (nouvelle.getKilometrage() <= 0)
                throw new VoitureException("le kilometrage est absent ou il est négatif");
            voitureExiste.setKilometrage(nouvelle.getKilometrage());
        }
        if (nouvelle.getActif() != null)
            voitureExiste.setActif(nouvelle.getActif());
        if (nouvelle.getRetire() != null)
            voitureExiste.setRetire(nouvelle.getRetire());
        if(voitureExiste.getActif() && voitureExiste.getRetire())
            throw new VoitureException("la voiture qui est retirée depuis le parc ne peut pas être activée !");
        //TODO : vérifier Permis ou pas
    }


    private static void verifierVoiture(VoitureRequestDto dto) throws VoitureException {
        if (dto == null)
            throw new VoitureException("le voitureRequestDto est null");
        if (dto.marque() == null || dto.marque().isBlank())
            throw new VoitureException("la marque de la voiture est absente");
        if (dto.modele() == null || dto.modele().isBlank())
            throw new VoitureException("le modèle de la voiture est absent");
        if (dto.couleur() == null || dto.couleur().isBlank())
            throw new VoitureException("la couleur de la voiture est absente");
        if (dto.nombrePlaces() == null || dto.nombrePlaces() <= 0)
            throw new VoitureException("le nombre de places est absent ou il est négatif");
        if (dto.nombrePortes() == null)
            throw new VoitureException("le nombre de portes est absent");
        if (dto.typeCarburant() == null)
            throw new VoitureException("le type de carburant de la voiture est absent");
        if (dto.transmission() == null)
            throw new VoitureException("la transmission de la voiture est absente");
        if (dto.climatisation() == null)
            throw new VoitureException("la climatisation est absent");
        if (dto.nombreBagages() == null || dto.nombreBagages() <= 0)
            throw new VoitureException("le nombre de bagages est absent ou il est négatif");
        if (dto.type() == null)
            throw new VoitureException("le type de la voiture est absent");
        if (dto.tarifParJour() == null || dto.tarifParJour() <= 0)
            throw new VoitureException("le tarif par jour est absent ou il est négatif");
        if (dto.kilometrage() == null || dto.kilometrage() <= 0)
            throw new VoitureException("le kilometrage est absent ou il est négatif");
        if (dto.actif() == null)
            throw new VoitureException("l'actif est absent");
        if (dto.retire() == null)
            throw new VoitureException("le retire est absent");
        if(dto.retire() && dto.actif())
            throw new VoitureException("la voiture qui est retirée depuis le parc ne peut pas être activée !");
        //TODO : vérifier Permis ou pas
    }

}

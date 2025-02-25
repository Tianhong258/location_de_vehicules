package com.accenture.service;

import com.accenture.exception.VoitureException;
import com.accenture.model.Permis;
import com.accenture.repository.VoitureDao;
import com.accenture.repository.entity.vehicule.Voiture;
import com.accenture.service.dto.vehiculeDto.VoitureRequestDto;
import com.accenture.service.dto.vehiculeDto.VoitureResponseAdminDto;
import com.accenture.service.mapper.vehiculeMapper.VoitureMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VoitureServiceImpl implements VoitureService {
    private final VoitureDao voitureDao;
    private final VoitureMapper voitureMapper;
    public static final String ID_NON_PRESENT = "id non présent";

    public VoitureServiceImpl(VoitureDao voitureDao, VoitureMapper voitureMapper) {
        this.voitureDao = voitureDao;
        this.voitureMapper = voitureMapper;
    }

    @Override
     public VoitureResponseAdminDto ajouter(VoitureRequestDto voitureRequestDto) throws VoitureException {
        verifierVoiture(voitureRequestDto);
        Voiture voiture= voitureMapper.toVoiture(voitureRequestDto);
        if(voiture.getNombrePlaces()>9)
            voiture.setPermis(Permis.D1);
        voiture.setPermis(Permis.B);
        Voiture voitureEnreg = voitureDao.save(voiture);
        return voitureMapper.toVoitureResponseAdminDto(voitureEnreg);
    }

    @Override
    public List<VoitureResponseAdminDto> trouverToutes(){
        return voitureDao.findByOrderByActifDescRetireDesc().stream().map(voitureMapper::toVoitureResponseAdminDto).toList();
    }


    @Override
    public VoitureResponseAdminDto trouver(long id) throws EntityNotFoundException {
        Optional<Voiture> optVoiture = voitureDao.findById(id);
        if(optVoiture.isEmpty())
            throw new EntityNotFoundException(ID_NON_PRESENT);
        Voiture voiture = optVoiture.get();
        return voitureMapper.toVoitureResponseAdminDto(voiture);
    }





    private static void verifierVoiture(VoitureRequestDto dto) throws VoitureException {
        if (dto == null)
            throw new VoitureException("le voitureRequestDto est null");
        if (dto.marque() == null || dto.marque().trim().isBlank())
            throw new VoitureException("la marque de la voiture est absente");
        if (dto.modele() == null || dto.modele().trim().isBlank())
            throw new VoitureException("le modèle de la voiture est absent");
        if (dto.nombrePlaces() == null || dto.nombrePlaces() <= 0)
            throw new VoitureException("le nombre de places est absent ou il est négatif");
        if (dto.nombrePortes() == null || dto.nombrePortes() <= 0)
            throw new VoitureException("le nombre de portes est absent ou il est négatif");
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



    }

}

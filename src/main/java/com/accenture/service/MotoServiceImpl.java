package com.accenture.service;

import com.accenture.exception.MotoException;
import com.accenture.model.Filtre;
import com.accenture.model.Permis;
import com.accenture.repository.MotoDao;
import com.accenture.repository.entity.vehicule.Moto;
import com.accenture.service.dto.vehiculeDto.MotoRequestDto;
import com.accenture.service.dto.vehiculeDto.MotoResponseAdminDto;
import com.accenture.service.mapper.vehiculeMapper.MotoMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MotoServiceImpl implements MotoService {
    private final MotoDao motoDao;
    private final MotoMapper motoMapper;
    public static final String ID_NON_PRESENT = "POM POM POM, id non présent";

    public MotoServiceImpl(MotoDao motoDao, MotoMapper motoMapper) {
        this.motoDao = motoDao;
        this.motoMapper = motoMapper;
    }

    @Override
    public MotoResponseAdminDto ajouter(MotoRequestDto motoRequestDto) throws MotoException {
        verifierMoto(motoRequestDto);
        Moto moto = motoMapper.toMoto(motoRequestDto);
        if (moto.getCylindree() <= 125 && moto.getPuissance()<= 11)
            moto.setPermis(List.of(Permis.A1,Permis.A2,Permis.A));
        if(moto.getPuissance()<=35)
            moto.setPermis(List.of(Permis.A2,Permis.A));
        else
            moto.setPermis(List.of(Permis.A));
        Moto motoEnreg = motoDao.save(moto);
        return motoMapper.toMotoResponseAdminDto(motoEnreg);

        //TODO : les conditions à vérifier
    }

    @Override
    public List<MotoResponseAdminDto> trouverToutes() {
        return motoDao.findByOrderByActifDescRetireDesc()
                .stream()
                .map(motoMapper::toMotoResponseAdminDto)
                .toList();
    }

    @Override
    public List<MotoResponseAdminDto> filtrer(Filtre filtre) {
        List<Moto> liste = switch (filtre) {
            case ACTIF -> motoDao.findByActifTrue();
            case NON_ACTIF -> motoDao.findByActifFalse();
            case RETIRE -> motoDao.findByRetireTrue();
            case NON_RETIRE -> motoDao.findByRetireFalse();
        };
        return liste.stream()
                .map(motoMapper::toMotoResponseAdminDto)
                .toList();
    }

    @Override
    public MotoResponseAdminDto trouver(long id) throws EntityNotFoundException {
        Optional<Moto> optMoto = motoDao.findById(id);
        if (optMoto.isEmpty())
            throw new EntityNotFoundException(ID_NON_PRESENT);
        Moto moto = optMoto.get();
        return motoMapper.toMotoResponseAdminDto(moto);
    }

    @Override
    public void supprimer(long id) throws EntityNotFoundException {
        if (motoDao.existsById(id))
            motoDao.deleteById(id);
        else
            throw new EntityNotFoundException(ID_NON_PRESENT);
        //TODO: si y a pas de location, fait ceci, sinon, mettre le retire en true
    }

    @Override
    public MotoResponseAdminDto modifier(long id, MotoRequestDto motoRequestDto) throws MotoException, EntityNotFoundException {
        Optional<Moto> optMoto = motoDao.findById(id);
        if (optMoto.isEmpty())
            throw new EntityNotFoundException(ID_NON_PRESENT);
        Moto motoExistante = optMoto.get();
        Moto nouvelle = motoMapper.toMoto(motoRequestDto);
        verifierEtRemplacer(nouvelle, motoExistante);
        Moto motoEnreg = motoDao.save(motoExistante);
        return motoMapper.toMotoResponseAdminDto(motoEnreg);
    }

    private static void verifierEtRemplacer(Moto nouvelle, Moto motoExiste) {
        if (nouvelle == null)
            throw new MotoException("la nouvelle moto est null");
        if (nouvelle.getMarque() != null) {
            if (nouvelle.getMarque().isBlank())
                throw new MotoException("la marque de la moto est absente");
            motoExiste.setMarque(nouvelle.getMarque());
        }
        if (nouvelle.getModele() != null) {
            if (nouvelle.getModele().isBlank())
                throw new MotoException("le modèle de la moto est absent");
            motoExiste.setModele(nouvelle.getModele());
        }
        if (nouvelle.getCouleur() != null) {
            if (nouvelle.getCouleur().isBlank())
                throw new MotoException("la couleur de la moto est absente");
            motoExiste.setCouleur(nouvelle.getCouleur());
        }

        if (nouvelle.getType() != null)
            motoExiste.setType(nouvelle.getType());
        if (nouvelle.getTarifParJour() != null) {
            if (nouvelle.getTarifParJour() <= 0)
                throw new MotoException("le tarif par jour est absent ou il est négatif");
            motoExiste.setTarifParJour(nouvelle.getTarifParJour());
        }
        if (nouvelle.getKilometrage() != null) {
            if (nouvelle.getKilometrage() <= 0)
                throw new MotoException("le kilometrage est absent ou il est négatif");
            motoExiste.setKilometrage(nouvelle.getKilometrage());
        }
        if (nouvelle.getActif() != null)
            motoExiste.setActif(nouvelle.getActif());
        if (nouvelle.getRetire() != null)
            motoExiste.setRetire(nouvelle.getRetire());
        if(motoExiste.getActif() && motoExiste.getRetire())
            throw new MotoException("la moto qui est retirée depuis le parc ne peut pas être activée !");
        //TODO : vérifier Permis ou pas
    }


    private static void verifierMoto(MotoRequestDto dto) throws MotoException {
        if (dto == null)
            throw new MotoException("le motoRequestDto est null");
        if (dto.marque() == null || dto.marque().isBlank())
            throw new MotoException("la marque de la moto est absente");
        if (dto.modele() == null || dto.modele().isBlank())
            throw new MotoException("le modèle de la moto est absent");
        if (dto.couleur() == null || dto.couleur().isBlank())
            throw new MotoException("la couleur de la moto est absente");
        if (dto.type() == null)
            throw new MotoException("le type de la moto est absent");
        if (dto.tarifParJour() == null || dto.tarifParJour() <= 0)
            throw new MotoException("le tarif par jour est absent ou il est négatif");
        if (dto.kilometrage() == null || dto.kilometrage() <= 0)
            throw new MotoException("le kilometrage est absent ou il est négatif");
        if (dto.actif() == null)
            throw new MotoException("l'actif est absent");
        if (dto.retire() == null)
            throw new MotoException("le retire est absent");
        if(dto.retire() && dto.actif())
            throw new MotoException("la moto qui est retirée depuis le parc ne peut pas être activée !");
    }
    //TODO : vérifier Permis ou pas
}

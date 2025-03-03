package com.accenture.service;

import com.accenture.exception.VehiculeException;
import com.accenture.model.Filtre;
import com.accenture.model.Permis;
import com.accenture.repository.MotoDao;
import com.accenture.repository.entity.vehicule.Moto;
import com.accenture.service.dto.vehicule.MotoRequestDto;
import com.accenture.service.dto.vehicule.MotoResponseAdminDto;
import com.accenture.service.mapper.vehicule.MotoMapper;
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

    /**
     * <p>La méthode <code>ajouter</code> permet d'ajouter une nouvelle moto en validant ses informations.</p>
     *
     * @param motoRequestDto Les informations de la moto à ajouter.
     * @return Une réponse contenant les informations de la moto ajoutée.
     * @throws VehiculeException Si les informations de la moto sont invalides.
     */
    @Override
    public MotoResponseAdminDto ajouter(MotoRequestDto motoRequestDto) throws VehiculeException {
        verifierMoto(motoRequestDto);
        Moto moto = motoMapper.toMoto(motoRequestDto);
        genererPermisMoto(moto);
        Moto motoEnreg = motoDao.save(moto);
        return motoMapper.toMotoResponseAdminDto(motoEnreg);

        //TODO : les conditions à vérifier
    }




    /**
     * <p>La méthode <code>trouverToutes</code> permet de récupérer toutes les motos, triées selon leur statut actif et retiré.</p>
     *
     * @return Une liste de réponses contenant les informations de toutes les motos.
     */
    @Override
    public List<MotoResponseAdminDto> trouverToutes() {
        return motoDao.findByOrderByActifDescRetireDesc()
                .stream()
                .map(motoMapper::toMotoResponseAdminDto)
                .toList();
    }

    /**
     * <p>La méthode <code>filtrer</code> permet de filtrer les motos selon leur statut actif ou retiré.</p>
     *
     * @param filtre Le filtre à appliquer pour récupérer les motos (actif, non actif, retiré, non retiré).
     * @return Une liste de réponses contenant les informations des motos correspondant au filtre.
     */
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
    /**
     * <p>La méthode <code>trouver</code> permet de récupérer une moto en fonction de son id.</p>
     *
     * @param id L'id de la moto à récupérer.
     * @return Une réponse contenant les informations de la moto trouvée.
     * @throws EntityNotFoundException Si la moto avec l'id spécifié n'est pas trouvée.
     */
    @Override
    public MotoResponseAdminDto trouver(long id) throws EntityNotFoundException {
        Optional<Moto> optMoto = motoDao.findById(id);
        if (optMoto.isEmpty())
            throw new EntityNotFoundException(ID_NON_PRESENT);
        Moto moto = optMoto.get();
        return motoMapper.toMotoResponseAdminDto(moto);
    }


    /**
     * <p>La méthode <code>supprimer</code> permet de supprimer une moto en fonction de son identifiant.</p>
     *
     * @param id L'identifiant de la moto à supprimer.
     * @throws EntityNotFoundException Si la moto avec l'id spécifié n'est pas trouvée.
     */
    @Override
    public void supprimer(long id) throws EntityNotFoundException {
        if (motoDao.existsById(id))
            motoDao.deleteById(id);
        else
            throw new EntityNotFoundException(ID_NON_PRESENT);
        //TODO: si y a pas de location, fait ceci, sinon, mettre le retire en true
    }


    /**
     * <p>La méthode <code>modifier</code> permet de modifier les informations d'une moto existante.</p>
     *
     * @param id L'id de la moto à modifier.
     * @param motoRequestDto Les nouvelles informations de la moto.
     * @return Une réponse contenant les informations mises à jour de la moto.
     * @throws VehiculeException Si les informations de la moto sont invalides.
     * @throws EntityNotFoundException Si la moto avec l'id spécifié n'est pas trouvée.
     */
    @Override
    public MotoResponseAdminDto modifier(long id, MotoRequestDto motoRequestDto) throws VehiculeException, EntityNotFoundException {
        Optional<Moto> optMoto = motoDao.findById(id);
        if (optMoto.isEmpty())
            throw new EntityNotFoundException(ID_NON_PRESENT);
        Moto motoExistante = optMoto.get();
        Moto nouvelle = motoMapper.toMoto(motoRequestDto);
        verifierEtRemplacer(nouvelle, motoExistante);
        Moto motoEnreg = motoDao.save(motoExistante);
        return motoMapper.toMotoResponseAdminDto(motoEnreg);
    }


    private static void genererPermisMoto(Moto moto) {
        if (moto.getCylindree() <= 125 && moto.getPuissance()<= 11)
            moto.setPermis(List.of(Permis.A1,Permis.A2,Permis.A));
        if( moto.getCylindree() > 125 && moto.getPuissance()<=35)
            moto.setPermis(List.of(Permis.A2,Permis.A));
        else
            moto.setPermis(List.of(Permis.A));
    }

    private static void verifierEtRemplacer(Moto nouvelle, Moto motoExiste) {
        if (nouvelle == null)
            throw new VehiculeException("la nouvelle moto est null");
        if (nouvelle.getMarque() != null) {
            if (nouvelle.getMarque().isBlank())
                throw new VehiculeException("la marque de la moto est absente");
            motoExiste.setMarque(nouvelle.getMarque());
        }
        if (nouvelle.getModele() != null) {
            if (nouvelle.getModele().isBlank())
                throw new VehiculeException("le modèle de la moto est absent");
            motoExiste.setModele(nouvelle.getModele());
        }
        if (nouvelle.getCouleur() != null) {
            if (nouvelle.getCouleur().isBlank())
                throw new VehiculeException("la couleur de la moto est absente");
            motoExiste.setCouleur(nouvelle.getCouleur());
        }

        if (nouvelle.getTypeMoto() != null)
            motoExiste.setTypeMoto(nouvelle.getTypeMoto());
        if (nouvelle.getTarif() != null) {
            if (nouvelle.getTarif() <= 0)
                throw new VehiculeException("le tarif par jour est absent ou il est négatif");
            motoExiste.setTarif(nouvelle.getTarif());
        }
        if (nouvelle.getKilometrage() != null) {
            if (nouvelle.getKilometrage() <= 0)
                throw new VehiculeException("le kilometrage est absent ou il est négatif");
            motoExiste.setKilometrage(nouvelle.getKilometrage());
        }
        if (nouvelle.getActif() != null)
            motoExiste.setActif(nouvelle.getActif());
        if (nouvelle.getRetire() != null)
            motoExiste.setRetire(nouvelle.getRetire());
        if(motoExiste.getActif() && motoExiste.getRetire())
            throw new VehiculeException("la moto qui est retirée depuis le parc ne peut pas être activée !");
        //TODO : vérifier Permis ou pas
    }


    private static void verifierMoto(MotoRequestDto dto) throws VehiculeException {
        if (dto == null)
            throw new VehiculeException("le motoRequestDto est null");
        if (dto.marque() == null || dto.marque().isBlank())
            throw new VehiculeException("la marque de la moto est absente");
        if (dto.modele() == null || dto.modele().isBlank())
            throw new VehiculeException("le modèle de la moto est absent");
        if (dto.couleur() == null || dto.couleur().isBlank())
            throw new VehiculeException("la couleur de la moto est absente");
        if (dto.type() == null)
            throw new VehiculeException("le type de la moto est absent");
        if (dto.tarif() == null || dto.tarif() <= 0)
            throw new VehiculeException("le tarif par jour est absent ou il est négatif");
        if (dto.kilometrage() == null || dto.kilometrage() <= 0)
            throw new VehiculeException("le kilometrage est absent ou il est négatif");
        if (dto.actif() == null)
            throw new VehiculeException("l'actif est absent");
        if (dto.retire() == null)
            throw new VehiculeException("le retire est absent");
        if(dto.retire() && dto.actif())
            throw new VehiculeException("la moto qui est retirée depuis le parc ne peut pas être activée !");
    }
    //TODO : vérifier Permis ou pas
}

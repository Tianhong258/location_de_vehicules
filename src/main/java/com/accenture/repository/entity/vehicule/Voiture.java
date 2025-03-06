package com.accenture.repository.entity.vehicule;
import com.accenture.model.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue(value="voiture")
public class Voiture extends Vehicule {
    
    private Integer nombrePlaces;
    @Enumerated(EnumType.STRING)
    private TypeCarburant typeCarburant;
    @Enumerated(EnumType.STRING)
    private NombrePortesVoiture nombrePortes;
    @Enumerated(EnumType.STRING)
    private Transmission transmission;
    private Boolean climatisation;
    private Integer nombreBagages;
    @Enumerated(EnumType.STRING)
    private TypeVoiture typeVoiture;
    @Enumerated(EnumType.STRING)
    private Permis permis;
    

}

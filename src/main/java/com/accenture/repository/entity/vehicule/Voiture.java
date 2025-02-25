package com.accenture.repository.entity.vehicule;
import com.accenture.model.Permis;
import com.accenture.model.Transmission;
import com.accenture.model.TypeCarburant;
import com.accenture.model.TypeVoiture;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@Entity
@Table(name = "voitures")
@DiscriminatorValue("Voiture")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Voiture extends Vehicule {
    @Column(nullable=false)
    private Integer nombrePlaces;
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private TypeCarburant typeCarburant;
    @Column(nullable=false)
    private Integer nombrePortes;
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private Transmission transmission;
    @Column(nullable=false)
    private Boolean climatisation;
    @Column(nullable=false)
    private Integer nombreBagages;
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private TypeVoiture type;
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private Permis permis;
    @Column(nullable=false)
    private Double tarifParJour;
    @Column(nullable=false)
    private Integer kilometrage;
    @Column(nullable=false)
    private Boolean actif = true;
    @Column(nullable=false)
    private Boolean retire = false;
}

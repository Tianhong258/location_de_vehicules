package com.accenture.repository.entity.vehicule;
import com.accenture.model.*;
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
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private NombrePortesVoiture nombrePortes;
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
    private Boolean actif;
    @Column(nullable=false)
    private Boolean retire;
}

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
@Table(name = "campingcars")
@DiscriminatorValue("CampingCar")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CampingCar extends Vehicule{
    @Column(nullable=false)
    private Integer nombrePlaces;
    @Column(nullable=false)
    private TypeCarburant typeCarburant;
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private Transmission transmission;
    @Column(nullable=false)
    private Boolean climatisation;
    @Column(nullable=false)
    private Double poids;
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private TypeCampingCar type;
    @Column(nullable=false)
    private Double hauteur;
    @Column(nullable=false)
    private Integer nombreCouchages;
    @Column(nullable=false)
    private Boolean equipementCuisine;
    @Column(nullable=false)
    private Boolean EquipementFrigo;
    @Column(nullable=false)
    private Boolean equipementDouche;
    @Column(nullable=false)
    private Boolean lingeLit;
    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
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

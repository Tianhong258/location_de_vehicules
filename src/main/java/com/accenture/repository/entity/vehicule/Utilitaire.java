package com.accenture.repository.entity.vehicule;

import com.accenture.model.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


//@Data
//@NoArgsConstructor
//@Entity
//@Table(name = "utilitaires")
//@DiscriminatorValue("Utilitaire")
//@ToString(callSuper = true)
//@EqualsAndHashCode(callSuper = true)
//public class Utilitaire extends Vehicule {
//    @Column(nullable=false)
//    private Integer nombrePlaces;
//    @Enumerated(EnumType.STRING)
//    @Column(nullable=false)
//    private TypeCarburant typeCarburant;
//    @Enumerated(EnumType.STRING)
//    @Column(nullable=false)
//    private Transmission transmission;
//    @Column(nullable=false)
//    private Boolean climatisation;
//    @Column(nullable=false)
//    private Integer chargeMax;
//    @Column(nullable=false)
//    private Double poids;
//    @Column(nullable=false)
//    private Double capacite;
//    @Enumerated(EnumType.STRING)
//    @Column(nullable=false)
//    private TypeUtilitaire type;
//    @Enumerated(EnumType.STRING)
//    @Column(nullable=false)
//    private Permis permis;
//    @Column(nullable=false)
//    private Double tarifParJour;
//    @Column(nullable=false)
//    private Integer kilometrage;
//    @Column(nullable=false)
//    private Boolean actif;
//    @Column(nullable=false)
//    private Boolean retire;
//}

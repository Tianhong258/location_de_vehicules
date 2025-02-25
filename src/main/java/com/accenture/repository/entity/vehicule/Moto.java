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
@Table(name = "motos")
@DiscriminatorValue("Moto")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Moto extends Vehicule {
    @Column(nullable=false)
    private Integer nombreCylindres;
    @Column(nullable=false)
    private Integer cylindree;
    @Column(nullable=false)
    private Integer poids;
    @Column(nullable=false)
    private Double puissance;
    @Column(nullable=false)
    private Double hauteurSelle;

    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private Transmission transmission;
    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private TypeMoto type;
    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private List<Permis> permis;
    @Column(nullable=false)
    private Double tarifParJour;
    @Column(nullable=false)
    private Integer kilometrage;
    @Column(nullable=false)
    private Boolean actif;
    @Column(nullable=false)
    private Boolean retire;






}

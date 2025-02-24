package com.accenture.repository.entity.vehicule;

import com.accenture.model.Permis;
import com.accenture.model.TypeCarburant;
import com.accenture.model.TypeMoto;
import com.accenture.model.TypeVoiture;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@Entity
@Table(name = "motos")
@DiscriminatorValue("Moto")
@ToString(callSuper = true)
public class Moto extends Vehicule {
    private int nombreCylindres;
    private int cylindree;
    private int poids;
    private double puissance;
    private double hauteurSelle;
    private String transmission;
    private Enum<TypeMoto> type;
    private Enum<Permis> permis;
    private double tarifParJour;
    private int kilometrage;
    private boolean actif;
    private boolean retire;






}

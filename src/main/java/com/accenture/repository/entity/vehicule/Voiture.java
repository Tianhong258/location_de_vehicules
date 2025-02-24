package com.accenture.repository.entity.vehicule;
import com.accenture.model.Permis;
import com.accenture.model.TypeCarburant;
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
@Table(name = "voitures")
@DiscriminatorValue("Voiture")
@ToString(callSuper = true)
public class Voiture extends Vehicule {
    private int nombrePlaces;
    private Enum<TypeCarburant> typeCarburant; //diesel, essence, électrique, hybride
    private int nombrePortes;
    private String transmission;  // auto / manuelle
    private boolean climatisation;
    private int nombreBagages;
    private Enum<TypeVoiture> type; // Citadine, Berline, SUV, Familiales, Voiture électrique, Voiture de luxe, ...
    private Enum<Permis> permis;
    private double tarifParJour;
    private int kilometrage;
    private boolean actif;
    private boolean retire;


}

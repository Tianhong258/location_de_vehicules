package com.accenture.repository.entity.location;

import com.accenture.model.Etat;
import com.accenture.repository.entity.utilisateur.Client;
import com.accenture.repository.entity.vehicule.Vehicule;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @ManyToOne
    Client client;
    @ManyToOne
    Vehicule vehicule;
//    @ManyToMany(cascade = CascadeType.PERSIST)
//    @JoinTable(name = "locationsAccessoires")
//    List<Accessoire> accessoire;
    LocalDate debut;
    LocalDate fin;
    Integer kilometres;
    Double montant;
    LocalDate dateValidation;
    @Enumerated(EnumType.STRING)
    Etat etat;
}

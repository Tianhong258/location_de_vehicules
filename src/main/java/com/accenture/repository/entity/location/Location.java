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
    private long id;
    @ManyToOne
    private Client client;
    @ManyToOne
    private Vehicule vehicule;
//    @ManyToMany(cascade = CascadeType.PERSIST)
//    @JoinTable(name = "locationsAccessoires")
//    List<Accessoire> accessoire;
    private LocalDate debut;
    private LocalDate fin;
    private Integer kilometrage;
    private Double montant;
    private LocalDate dateValidation = LocalDate.now();
    @Enumerated(EnumType.STRING)
    private Etat etat;
}

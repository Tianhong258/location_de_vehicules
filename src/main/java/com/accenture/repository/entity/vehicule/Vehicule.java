package com.accenture.repository.entity.vehicule;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "DISCR")
public class Vehicule {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(nullable=false)
    private String marque;
    @Column(nullable=false)
    private String modele;
    @Column(nullable=false)
    private String couleur;



}

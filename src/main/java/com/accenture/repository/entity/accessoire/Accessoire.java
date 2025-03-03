//package com.accenture.repository.entity.accessoire;
//
//import com.accenture.model.CategorieVehicule;
//import jakarta.persistence.*;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@NoArgsConstructor
//@Entity
//@Table(name = "accessoires")
//public class Accessoire {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//    @Column(nullable=false)
//    @Enumerated(EnumType.STRING)
//    private CategorieVehicule categorie;
//    @Column(nullable=false)
//    private String nom;
//    @Column(nullable=false)
//    private Integer quantite;
//}

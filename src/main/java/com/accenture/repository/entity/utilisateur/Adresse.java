package com.accenture.repository.entity.utilisateur;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "adresses")
public class Adresse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable=false)
    private String rue;
    @Column(nullable=false)
    private String codePostal;
    @Column(nullable=false)
    private String ville;
}

package com.accenture.repository.entity.utilisateur;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "DISCR")
public abstract class UtilisateurConnecte {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;
    private String nom;
    private String prenom;
    @Column(unique = true)//TODO: à tester
    private String email;
    private String password;

}

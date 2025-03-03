package com.accenture.repository.entity.utilisateur;


import com.accenture.model.Permis;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;


@Data
@NoArgsConstructor
@Entity
@Table(name = "clients")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Client extends UtilisateurConnecte{

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(nullable=false)
    private Adresse adresse;
    private LocalDate dateNaissance;
    private LocalDate dateInscription = LocalDate.now();
    @Enumerated(EnumType.STRING)
    private List<Permis> listePermis;
    private Boolean desactive = false;
    private String role = "ROLE_CLIENT";


}

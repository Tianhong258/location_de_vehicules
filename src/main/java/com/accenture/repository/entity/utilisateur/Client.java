package com.accenture.repository.entity.utilisateur;


import com.accenture.model.Permis;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "clients")
@DiscriminatorValue("C")
@ToString(callSuper = true)
public class Client extends UtilisateurConnecte{

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Adresse adresse;
    private LocalDate dateNaissance;
    private LocalDate dateInscription = LocalDate.now();
    @Enumerated(EnumType.STRING)
    private List<Permis> listePermis;
    private boolean desactive = false;


}

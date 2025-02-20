package com.accenture.repository.entity.utilisateur;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@Entity
@Table(name = "administrateurs")
@DiscriminatorValue("A")
@ToString(callSuper = true)
public class Admin extends UtilisateurConnecte{

    private String fonction;


}

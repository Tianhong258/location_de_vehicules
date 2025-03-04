package com.accenture.repository.entity.utilisateur;


import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@Entity
@Table(name = "administrateurs")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Admin extends UtilisateurConnecte{
    private String fonction;
    private String role = "ADMIN";

}

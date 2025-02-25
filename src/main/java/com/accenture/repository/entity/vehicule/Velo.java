package com.accenture.repository.entity.vehicule;

import com.accenture.model.TypeVelo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@Entity
@Table(name = "velos")
@DiscriminatorValue("Velo")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Velo extends Vehicule{
    @Column(nullable=false)
    private Integer tailleCadre;
    @Column(nullable=false)
    private Double poids;
    @Column(nullable=false)
    private Boolean electrique; //o Si oui, capacité de batterie et autonomie
    @Column(nullable=false)
    private Boolean FreinsADisque;
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private TypeVelo type;


}

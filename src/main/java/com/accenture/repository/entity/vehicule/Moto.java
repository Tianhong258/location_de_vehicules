package com.accenture.repository.entity.vehicule;

import com.accenture.model.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue(value="moto")
public class Moto extends Vehicule {
    
    private Integer nombreCylindres;
    
    private Integer cylindree;
    
    private Integer poids;
    
    private Double puissance;
    
    private Double hauteurSelle;
    
    @Enumerated(EnumType.STRING)
    private Transmission transmission;
    
    @Enumerated(EnumType.STRING)
    private TypeMoto typeMoto;
    
    @Enumerated(EnumType.STRING)
    private Permis permis;





}

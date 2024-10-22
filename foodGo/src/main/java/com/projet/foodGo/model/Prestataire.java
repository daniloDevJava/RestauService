package com.projet.foodGo.model;

import com.projet.foodGo.model.enumType.NatureCompte;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

//import javax.xml.stream.Location;
import java.util.List;

@Entity
@Getter
@Setter
public class Prestataire extends Users {

    @Column(unique = true,nullable = false)
    private String nom;
    private NatureCompte natureCompte;

    @OneToMany(mappedBy = "prestataire", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Produits> listProduits;
    private Double noteMoyenne;


    //private Location geography;


}

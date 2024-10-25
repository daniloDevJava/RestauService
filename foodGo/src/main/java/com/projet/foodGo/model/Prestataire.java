package com.projet.foodGo.model;

import com.projet.foodGo.model.enumType.NatureCompte;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.geo.Point;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Prestataire extends Users {

    private NatureCompte natureCompte;

    @OneToMany(mappedBy = "prestataire", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Produits> listProduits= new ArrayList<>();
    private Double noteMoyenne;
    @Column(nullable = false)
    private Point geography;


}

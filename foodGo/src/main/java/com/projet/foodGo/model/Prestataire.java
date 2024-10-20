package com.projet.foodGo.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

//import javax.xml.stream.Location;
import java.util.List;

@Entity
@Getter
@Setter
public class Prestataire extends User {

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ProduitAlimentaire> listProduits;
    private Double noteMoyenne;

    public List<ProduitAlimentaire> getListProduits() {
        return listProduits;
    }

    public void setListProduits(List<ProduitAlimentaire> listProduits) {
        this.listProduits = listProduits;
    }

    public Double getNoteMoyenne() {
        return noteMoyenne;
    }

    public void setNoteMoyenne(Double noteMoyenne) {
        this.noteMoyenne = noteMoyenne;
    }

    //private Location geography;


}

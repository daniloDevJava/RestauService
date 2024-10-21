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
    private String cheminVersImages;
    private NatureCompte natureCompte;

    @OneToMany(mappedBy = "prestataire", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Produits> listProduits;
    private Double noteMoyenne;

    public List<Produits> getListProduits() {
        return listProduits;
    }

    public void setListProduits(List<Produits> listProduits) {
        this.listProduits = listProduits;
    }

    public Double getNoteMoyenne() {
        return noteMoyenne;
    }

    public void setNoteMoyenne(Double noteMoyenne) {
        this.noteMoyenne = noteMoyenne;
    }

    @Override
    public String getNom() {
        return nom;
    }

    @Override
    public void setNom(String nom) {
        this.nom = nom;
    }

    public NatureCompte getNatureCompte() {
        return natureCompte;
    }

    public void setNatureCompte(NatureCompte natureCompte) {
        this.natureCompte = natureCompte;
    }

    public String getCheminVersImages() {
        return cheminVersImages;
    }

    public void setCheminVersImages(String cheminVersImages) {
        this.cheminVersImages = cheminVersImages;
    }

    //private Location geography;


}

package com.projet.foodGo.dto;


import lombok.Getter;
import lombok.Setter;
import java.util.UUID;


@Getter
@Setter
public class ProduitAlimentaireDto {

    private UUID id;

    private String libelle;

    private int quantiteStock;
    private int prixUnitaire;


   // private PrestataireDto prestataire;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getQuantiteStock() {
        return quantiteStock;
    }

    public void setQuantiteStock(int quantiteStock) {
        this.quantiteStock = quantiteStock;
    }

    public int getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(int prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

   /* public PrestataireDto getPrestataire() {
        return prestataire;
    }

    public void setPrestataire(PrestataireDto prestataire) {
        this.prestataire = prestataire;
    }*/


}

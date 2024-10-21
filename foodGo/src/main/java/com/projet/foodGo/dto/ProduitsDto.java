package com.projet.foodGo.dto;


import com.projet.foodGo.model.enumType.TypeProduct;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
public class ProduitsDto {

    private UUID id;

    private String libelle;

    private int quantiteStock;
    private int prixUnitaire;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private TypeProduct typeProduct;


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

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public TypeProduct getTypeProduct() {
        return typeProduct;
    }

    public void setTypeProduct(TypeProduct typeProduct) {
        this.typeProduct = typeProduct;
    }

   /* public PrestataireDto getPrestataire() {
        return prestataire;
    }

    public void setPrestataire(PrestataireDto prestataire) {
        this.prestataire = prestataire;
    }*/


}

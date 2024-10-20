package com.projet.foodGo.dto;

import com.projet.foodGo.model.ProduitAlimentaire;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;


@Getter
@Setter
public class CommandeDto {

    private UUID id;
    private Double prixTotal;
    private List<ProduitAlimentaireDto> produits;
    private boolean withLivraison;
    private UUID idPrestataire;
    private UserDto user;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Double getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(Double prixTotal) {
        this.prixTotal = prixTotal;
    }

    public List<ProduitAlimentaireDto> getProduits() {
        return produits;
    }

    public void setProduits(List<ProduitAlimentaireDto> produits) {
        this.produits = produits;
    }

    public boolean isWithLivraison() {
        return withLivraison;
    }

    public void setWithLivraison(boolean withLivraison) {
        this.withLivraison = withLivraison;
    }

    public UUID getIdPrestataire() {
        return idPrestataire;
    }

    public void setIdPrestataire(UUID idPrestataire) {
        this.idPrestataire = idPrestataire;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}

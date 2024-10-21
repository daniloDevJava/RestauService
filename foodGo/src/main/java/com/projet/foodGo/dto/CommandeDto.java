package com.projet.foodGo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.UUID;


@Getter
@Setter
public class CommandeDto {

    private UUID id;
    private Double prixTotal;
    private List<ProduitsDto> produits;
    private boolean withLivraison;
    private UUID idPrestataire;
    private UserDto user;
    private Map<UUID,Integer> productValeur;

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

    public List<ProduitsDto> getProduits() {
        return produits;
    }

    public void setProduits(List<ProduitsDto> produits) {
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

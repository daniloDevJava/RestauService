package com.projet.foodGo.dto;


import lombok.Getter;
import lombok.Setter;


import java.util.UUID;


@Getter
@Setter
public class LivraisonDto {

    private UUID id;

    private float timeLivraison;

    private Double fraisLivraison;
    private String numeroLivreur;
    private CommandeDto commandeDto;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public float getTimeLivraison() {
        return timeLivraison;
    }

    public void setTimeLivraison(float timeLivraison) {
        this.timeLivraison = timeLivraison;
    }

    public Double getFraisLivraison() {
        return fraisLivraison;
    }

    public void setFraisLivraison(Double fraisLivraison) {
        this.fraisLivraison = fraisLivraison;
    }

    public String getNumeroLivreur() {
        return numeroLivreur;
    }

    public void setNumeroLivreur(String numeroLivreur) {
        this.numeroLivreur = numeroLivreur;
    }

    public CommandeDto getCommandeDto() {
        return commandeDto;
    }

    public void setCommandeDto(CommandeDto commandeDto) {
        this.commandeDto = commandeDto;
    }
}

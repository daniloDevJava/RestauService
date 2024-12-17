package com.projet.foodGo.dto;


import com.projet.foodGo.model.enumType.LivraisonState;
import lombok.Getter;
import lombok.Setter;


import java.util.UUID;


@Getter
@Setter
public class LivraisonDto {

    private UUID id;

    private float timeLivraison;
    private LivraisonState etatLivraison;
    private double fraisLivraison;
    private String numeroLivreur;
    private UUID commandeId;


}

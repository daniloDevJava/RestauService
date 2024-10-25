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
    private Map<UUID,Integer> productsNumbers;

}

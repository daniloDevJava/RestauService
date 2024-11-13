package com.projet.foodGo.dto;

import com.projet.foodGo.model.enumType.Etat;
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
    private boolean withLivraison;
    private UUID idPrestataire;
    private UUID idUser;
    private Map<UUID ,Integer> productQuantite;
    private Etat etat;
    private UserDto userDto;
}

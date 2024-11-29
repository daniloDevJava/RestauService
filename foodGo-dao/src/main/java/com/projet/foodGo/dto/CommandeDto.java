package com.projet.foodGo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.projet.foodGo.model.enumType.Etat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.UUID;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommandeDto {
    private UUID id;
    private Double prixTotal;
    @NotNull(message = "you should to tell if the command is with livraison")
    @NotEmpty(message = "TRUE_OR_FALSE")
    private boolean withLivraison;
    private UUID idPrestataire;
    private UUID idUser;
    private Map<UUID ,Integer> productQuantite;
    private Etat etat;
    private UserDto userDto;
}

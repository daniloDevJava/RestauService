package com.projet.foodGo.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.projet.foodGo.model.enumType.TypeProduct;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProduitsDto {

    private UUID id;
    private int quantiteVenduLocal;
    @NotNull(message = "the libelle is mandatory")
    @NotEmpty(message = "the libelle can't be empty")
    private String libelle;
    @NotNull(message = "the stock's quantity is mandatory")
    private int quantiteStock;
    @NotNull(message = "the unit's price is mandatory")
    private int prixUnitaire;
    private List<Long> imagesList;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;


}

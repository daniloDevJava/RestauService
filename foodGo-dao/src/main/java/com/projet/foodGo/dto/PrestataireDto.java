package com.projet.foodGo.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.projet.foodGo.model.enumType.NatureCompte;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PrestataireDto extends UserDto {

    private List<UUID> listProduits;
    private Double noteMoyenne;
    @NotNull(message = "la nature du compte doit etre specifiee")
    private NatureCompte natureCompte;
    private Double longitude;
    private Double latitude;
    @NotNull(message = "the address is mandatory")
    @NotEmpty(message = "the adress ")
    private String address;
    private double montantCompte;

}

package com.projet.foodGo.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientDto extends UserDto {

    private UUID id;
    private String Prenom;
    @NotNull(message = "The date of birth is mandatory")
    @NotEmpty(message = "The date of birth is not empty")
    private LocalDate dateOfBirth;
    private String adresse;
    private double montantCompte;
    @NotNull(message = "The number cni is mandatory")
    @Size(min=10,message = "The cni's number have 10 characters minimum")
    private String numeroCNI;


}

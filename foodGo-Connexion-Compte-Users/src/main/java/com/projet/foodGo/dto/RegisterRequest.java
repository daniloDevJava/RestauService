package com.projet.foodGo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.projet.foodGo.model.enumType.NatureCompte;
import com.projet.foodGo.model.enumType.RoleUser;
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
public class RegisterRequest {
    @NotNull(message = "The name is mandatory")
    @NotEmpty(message = "The name of user is not empty")
    private String username;
    @NotNull(message = "The mail address is mandatory")
    @Size(min = 1,max = 50,message = "The mail is between 1 to 50 characters")
    private String email;
    @NotNull(message = "The password must be specified")
    @Size(min=8,message = "The password has 8 characters minimum")
    private String password;
    @NotNull(message = "the role must be specified")
    private RoleUser role;
    private String adresse;

    // Champs spécifiques pour Client
    private String prenom;

    private LocalDate dateOfBirth;

    private String numeroCNI;

    //champ pour l'admin
    private UUID entryKey;

    // Champs spécifiques pour Prestataire
    private NatureCompte natureCompte;
    private Double latitude;
    private Double longitude;

    private double montantCompte;
}

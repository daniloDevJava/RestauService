package com.projet.foodGo.external;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.projet.foodGo.model.enumType.NatureCompte;
import com.projet.foodGo.model.enumType.RoleUser;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private RoleUser role;

    // Champs spécifiques pour Client
    private String prenom;
    private LocalDate dateOfBirth;
    private String adresse;
    private String numeroCNI;
    private UUID entryKey;

    // Champs spécifiques pour Prestataire
    private NatureCompte natureCompte;
    private Double latitude;
    private Double longitude;



    private double montantCompte;
}

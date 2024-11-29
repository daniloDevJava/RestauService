package com.projet.foodGo.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;


@Getter
@Setter
public class ClientDto extends UserDto {

    private UUID id;
    private String Prenom;
    private LocalDate dateOfBirth;
    private String adresse;
    private double montantCompte;
    private String numeroCNI;


}

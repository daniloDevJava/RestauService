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

    private String numeroCNI;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getNumeroCNI() {
        return numeroCNI;
    }

    public void setNumeroCNI(String numeroCNI) {
        this.numeroCNI = numeroCNI;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        this.Prenom = prenom;
    }
}

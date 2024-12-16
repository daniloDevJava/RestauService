package com.projet.foodGo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Getter
@Setter
public class Client extends Users {


    private LocalDate dateOfBirth;
    private String adresse;
    @Column(unique = true)
    private String nomPrenom;
    @Column(name = "CNI",unique = true)
    private String numeroCNI;


}

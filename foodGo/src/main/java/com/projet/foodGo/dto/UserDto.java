package com.projet.foodGo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
public class UserDto {

    protected UUID id;

    protected String nom;
    protected String motDePasse;
    protected String adresseMail;
    protected LocalDateTime createAt;
    protected LocalDateTime updateAt;

}

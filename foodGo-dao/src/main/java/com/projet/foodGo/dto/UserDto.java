package com.projet.foodGo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.projet.foodGo.model.enumType.RoleUser;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

    protected UUID id;
    @NotNull(message = "The name is mandatory")
    @NotEmpty(message = "The name of user is not empty")
    protected String nom;
    @NotNull(message = "The password must be specified")
    @Size(min=8,message = "The password has 8 characters minimum")
    protected String motDePasse;
    @NotNull(message = "The mail address is mandatory")
    @Size(min = 1,max = 50,message = "The mail is between 1 to 50 characters")
    protected String adresseMail;
    protected LocalDateTime createAt;
    protected LocalDateTime updateAt;
    private RoleUser role;
    private double montantCompte;

}

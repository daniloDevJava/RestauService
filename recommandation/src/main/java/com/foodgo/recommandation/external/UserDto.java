package com.foodgo.recommandation.external;

import com.foodgo.recommandation.external.enumType.RoleUser;
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
    private RoleUser role;
    private double montantCompte;
}

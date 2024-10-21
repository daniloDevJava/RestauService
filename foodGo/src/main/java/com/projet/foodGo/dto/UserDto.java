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
    protected String adresseMail;
    protected LocalDateTime createAt;
    protected LocalDateTime updateAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }


    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public String getAdresseMail() {
        return adresseMail;
    }
    public void setAdresseMail(String adresseMail) {
        this.adresseMail = adresseMail;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom=nom;
    }
}

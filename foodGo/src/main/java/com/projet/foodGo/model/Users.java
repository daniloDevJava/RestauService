package com.projet.foodGo.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    protected String nom;
    @Column(nullable = false,unique = true)
    protected String adresseMail;

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    protected LocalDateTime createAt;

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    @UpdateTimestamp
    protected LocalDateTime updateAt;
    protected LocalDateTime deleteAt;


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

    public void setDeleteAt(LocalDateTime deleteAt) {
        this.deleteAt = deleteAt;
    }

}

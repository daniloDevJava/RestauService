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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nom_et_prenom")
    protected String nomEtPrenom;
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

    public String getNomEtPrenom() {
        return nomEtPrenom;
    }

    public void setNomEtPrenom(String nomEtPrenom) {
        this.nomEtPrenom = nomEtPrenom;
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

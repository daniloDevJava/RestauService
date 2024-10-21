package com.projet.foodGo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Admin extends Users {


    @Column(unique = true,nullable = false)
    private String nom;

    private String EntryKey;

    public String getEntryKey() {
        return EntryKey;
    }

    public void setEntryKey(String entryKey) {
        EntryKey = entryKey;
    }

    @Override
    public String getNom() {
        return nom;
    }

    @Override
    public void setNom(String nom) {
        this.nom = nom;
    }
}

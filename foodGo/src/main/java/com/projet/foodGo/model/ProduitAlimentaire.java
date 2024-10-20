package com.projet.foodGo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class ProduitAlimentaire {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String libelle;

    private int quantiteStock;
    private int prixUnitaire;

    @ManyToOne
    @JoinColumn(name = "prestataire_id")
    private Prestataire prestataire;


    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private LocalDateTime createAt;
    @UpdateTimestamp
    private LocalDateTime updateAt;
    private LocalDateTime deleteAt;

    public int getQuantiteStock() {
        return quantiteStock;
    }

    public void setQuantiteStock(int quantiteStock) {
        this.quantiteStock = quantiteStock;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
    public LocalDateTime createAt() {
        return createAt;
    }

    public ProduitAlimentaire setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
        return this;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(int prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public Prestataire getPrestataire() {
        return prestataire;
    }

    public void setPrestataire(Prestataire prestataire) {
        this.prestataire = prestataire;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public LocalDateTime getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(LocalDateTime deleteAt) {
        this.deleteAt = deleteAt;
    }
}

package com.projet.foodGo.model;

import com.projet.foodGo.model.enumType.LivraisonState;
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
public class Livraison {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private float timeLivraison;
    private LivraisonState etatLivraison;
    private Double fraisLivraison;
    private String numeroLivreur;

    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private LocalDateTime createAt;
    @UpdateTimestamp
    private LocalDateTime updateAt;
    private LocalDateTime deleteAt;

    @OneToOne(optional = false)
    @JoinColumn(name = "commande_id",updatable = false,unique = true)
    private Commande commande;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public float getTimeLivraison() {
        return timeLivraison;
    }

    public void setTimeLivraison(float timeLivraison) {
        this.timeLivraison = timeLivraison;
    }

    public Double getFraisLivraison() {
        return fraisLivraison;
    }

    public void setFraisLivraison(Double fraisLivraison) {
        this.fraisLivraison = fraisLivraison;
    }

    public String getNumeroLivreur() {
        return numeroLivreur;
    }

    public void setNumeroLivreur(String numeroLivreur) {
        this.numeroLivreur = numeroLivreur;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
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

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public LivraisonState getEtatLivraison() {
        return etatLivraison;
    }

    public void setEtatLivraison(LivraisonState etatLivraison) {
        this.etatLivraison = etatLivraison;
    }
}

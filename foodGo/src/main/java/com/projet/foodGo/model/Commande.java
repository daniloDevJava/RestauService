package com.projet.foodGo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Double prixTotal;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ProduitAlimentaire> produits;

    private boolean withLivraison;

    @Column(nullable = false)
    private UUID idPrestataire;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private LocalDateTime createAt;
    @UpdateTimestamp
    private LocalDateTime updateAt;
    private LocalDateTime deleteAt;

    public Double getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(Double prixTotal) {
        this.prixTotal = prixTotal;
    }

    public List<ProduitAlimentaire> getProduits() {
        return produits;
    }

    public void setProduits(List<ProduitAlimentaire> produits) {
        this.produits = produits;
    }

    public boolean isWithLivraison() {
        return withLivraison;
    }

    public void setWithLivraison(boolean withLivraison) {
        this.withLivraison = withLivraison;
    }

    public UUID getIdPrestataire() {
        return idPrestataire;
    }
    public void setIdPrestataire(UUID idPrestataire){
        this.idPrestataire=idPrestataire;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}

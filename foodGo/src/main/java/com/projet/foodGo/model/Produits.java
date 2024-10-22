package com.projet.foodGo.model;

import com.projet.foodGo.model.enumType.TypeProduct;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "produits")
@Getter
@Setter
public class Produits {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String libelle;

    private int quantiteStock;
    private int prixUnitaire;

    @ManyToOne
    @JoinColumn(name = "prestataire_id")
    private Prestataire prestataire;
    private TypeProduct typeProduct;

    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private LocalDateTime createAt;
    private String cheminVersImages;
    @UpdateTimestamp
    private LocalDateTime updateAt;
    private LocalDateTime deleteAt;
}

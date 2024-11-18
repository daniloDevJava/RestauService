package com.projet.foodGo.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true,nullable = false)
    protected String nom;
    @Column(nullable = false)
    protected String motDePasse;
    @Column(nullable = false,unique = true)
    protected String adresseMail;

    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    protected LocalDateTime createAt;

    @UpdateTimestamp
    protected LocalDateTime updateAt;
    protected LocalDateTime deleteAt;



}

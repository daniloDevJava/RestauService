package com.projet.foodGo.model;

import com.projet.foodGo.model.enumType.RoleUser;
import lombok.Getter;
import lombok.Setter;


import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@Entity(name = "users")
public class Utilisateur implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;
    private String email;

    @Column(name = "username", nullable = false)
    private String username;
    private boolean actif = false;
    @Column(name = "password", nullable = false)
    private String mdp;

    @Enumerated(EnumType.STRING)
    private RoleUser role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }



    @Override
    public boolean isAccountNonExpired() {
        return actif;
    }

    @Override
    public boolean isAccountNonLocked() {
        return actif;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return actif;
    }

    @Override
    public boolean isEnabled() {
        return actif;
    }
    @Override
    public String getPassword() {
        return mdp;
    }
}

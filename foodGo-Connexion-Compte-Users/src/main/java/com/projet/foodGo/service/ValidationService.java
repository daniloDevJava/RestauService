package com.projet.foodGo.service;

import com.projet.foodGo.model.Utilisateur;
import com.projet.foodGo.model.Validation;
import com.projet.foodGo.repository.ValidationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
public class ValidationService {

    private final ValidationRepository validationRepository;

    private final EmailService emailService;

    /**
     * Génère un code de validation pour l'utilisateur et l'envoie par e-mail.
     *
     * @return
     */
    public String generateAndSendValidationCode(Utilisateur utilisateur) {
        // Générer un code de validation à 6 chiffres
        String code = String.valueOf((int) (Math.random() * 900000) + 100000);

        // Créer une instance de Validation
        Validation validation = new Validation();
        validation.setUtilisateur(utilisateur);
        validation.setCode(code);
        validation.setCreation(Instant.now());
        validation.setExpiration(Instant.now().plusSeconds(10 * 60)); // Valide pendant 10 minutes

        // Enregistrer la validation
        validationRepository.save(validation);

        // Envoyer l'e-mail
        emailService.sendValidationEmail(utilisateur.getEmail(), code);
        return code;
    }

    /**
     * Valide un code et active l'utilisateur s'il est valide.
     */
    public boolean validateCode(String email, String code) {
        Validation validation = validationRepository.findByUtilisateur_EmailAndCode(email, code)
                .orElseThrow(() -> new IllegalArgumentException("Code de validation invalide ou expiré"));

        // Vérifier la date d'expiration
        if (validation.getExpiration().isBefore(Instant.now())) {
            throw new IllegalArgumentException("Le code de validation a expiré.");
        }

        // Activer l'utilisateur et mettre à jour l'activation
        validation.setActivation(Instant.now());
        Utilisateur utilisateur = validation.getUtilisateur();
        utilisateur.setActif(true);

        validationRepository.save(validation); // Mise à jour de la validation
        return true;
    }
}


package com.projet.foodGo.service;

import com.projet.foodGo.exceptions.BusinessException;
import com.projet.foodGo.exceptions.ErrorModel;
import com.projet.foodGo.model.Utilisateur;
import com.projet.foodGo.model.Validation;
import com.projet.foodGo.repository.UtilisateurRepository;
import com.projet.foodGo.repository.ValidationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ValidationService {

    private final ValidationRepository validationRepository;

    private final EmailService emailService;
    private final UtilisateurRepository utilisateurRepository;

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
    public boolean validateCode(String email, String code) throws BusinessException {
        List<ErrorModel> errorModels=new ArrayList<>();

        Optional<Validation> optionalvalidation = validationRepository.findByUtilisateur_Email_AndCode(email, code);
        if(optionalvalidation.isPresent()){
            Validation validation=optionalvalidation.get();
            // Vérifier la date d'expiration
            if (validation.getExpiration().isBefore(Instant.now())) {
                ErrorModel errorModel=new ErrorModel();
                errorModel.setCode("TIME_OUT_FOR_ACTION");
                errorModel.setMessage("Le code de validation a expiré.");
                errorModels.add(errorModel);
                Utilisateur utilisateur = validation.getUtilisateur();
                validationRepository.delete(validation);
                utilisateurRepository.delete(utilisateur);
                throw new BusinessException(errorModels);
            }

            // Activer l'utilisateur et mettre à jour l'activation
            validation.setActivation(Instant.now());
            Utilisateur utilisateur = validation.getUtilisateur();
            utilisateur.setActif(true);

            validationRepository.save(validation);
            return true;// Mise à jour de la validation
        }
        else
        {
            ErrorModel errorModel=new ErrorModel();
            errorModel.setCode("INVALID_PARAMETERS");
            errorModel.setMessage("le code ou l'adresse mail est inconnue");
            errorModels.add(errorModel);
            throw new BusinessException(errorModels);
        }




    }
}


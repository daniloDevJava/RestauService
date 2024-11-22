package com.projet.foodGo.service;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    /**
     * Envoie un e-mail contenant un code de validation.
     *
     * @param to   Adresse e-mail du destinataire.
     * @param code Code de validation à envoyer.
     */
    public void sendValidationEmail(String to, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Votre code de validation");
        message.setText(
                "Bonjour,\n\n" +
                        "Voici votre code de validation : " + code + "\n" +
                        "Ce code est valable pendant 10 minutes.\n\n" +
                        "Merci,\nL'équipe de FoodGo"
        );
        mailSender.send(message);
    }
}


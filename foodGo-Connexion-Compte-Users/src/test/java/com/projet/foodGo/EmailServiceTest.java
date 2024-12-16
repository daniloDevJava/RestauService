package com.projet.foodGo;


import com.projet.foodGo.service.EmailService;

import org.mockito.InjectMocks;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    @Test
    public void testSendValidationEmail() {
        String to = "brice.dan@facsciences-uy1.cm";
        String code = "123456";

        // Appel de la méthode à tester
        emailService.sendValidationEmail(to, code);

        // Vérification que le mail a été envoyé
        verify(mailSender).send(any(SimpleMailMessage.class));
    }
}

package com.projet.foodGo.service;

import com.projet.foodGo.exceptions.BusinessException;
import com.projet.foodGo.exceptions.ErrorModel;
import com.projet.foodGo.external.AdminDto;
import com.projet.foodGo.external.ClientDto;
import com.projet.foodGo.external.PrestataireDto;
import com.projet.foodGo.external.RegisterRequest;
import com.projet.foodGo.model.Utilisateur;
import com.projet.foodGo.repository.UtilisateurRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserService {
    private final UtilisateurRepository utilisateurRepository;
    private final ValidationService validationService;
    private final EmailService emailService;
    private final WebClient.Builder webClientBuilder;

    /**
     * Enregistre un utilisateur en fonction de son rôle.
     *
     * @return
     */
    public RegisterRequest registerUser(RegisterRequest request) throws BusinessException {
        List<ErrorModel> errorModelList=new ArrayList<>();
        // Étape 1 : Créer un utilisateur inactif localement
        if(LocalDate.now().getYear()-request.getDateOfBirth().getYear()<15){
            ErrorModel errorModel=new ErrorModel();
            errorModel.setCode("NOT_AUTHORIZED");
            errorModel.setMessage("vous n'avez pas l'age minimale pour creer un compte");
            errorModelList.add(errorModel);
            throw new BusinessException(errorModelList);
        } else if (Objects.equals(request.getPassword(), "")) {
            ErrorModel errorModel=new ErrorModel();
            errorModel.setCode("INVALID_FORMAT");
            errorModel.setMessage("Le mot de pass ne peut etre vide");
            errorModelList.add(errorModel);
            throw new BusinessException(errorModelList);

        } else if (!request.getEmail().contains("@")) {
            ErrorModel errorModel=new ErrorModel();
            errorModel.setCode("INVALID_FORMAT");
            errorModel.setMessage("Le mot de pass ne peut etre vide");
            errorModelList.add(errorModel);
            throw new BusinessException(errorModelList);

        }
        else if (!request.getEmail().contains(".")) {
            ErrorModel errorModel=new ErrorModel();
            errorModel.setCode("INVALID_FORMAT");
            errorModel.setMessage("Le mot de pass ne peut etre vide");
            errorModelList.add(errorModel);
            throw new BusinessException(errorModelList);

        }

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setUsername(request.getUsername());
        utilisateur.setEmail(request.getEmail());
        utilisateur.setMdp(request.getPassword()); // Assurez-vous de hacher le mot de passe avant enregistrement
        utilisateur.setRole(request.getRole());
        utilisateur.setActif(false);

        utilisateur = utilisateurRepository.save(utilisateur);

        // Étape 2 : Générer et envoyer le code de validation
        String code = validationService.generateAndSendValidationCode(utilisateur);

        // Étape 3 : Appeler le DAO en fonction du rôle
        switch (request.getRole()) {
            case ADMIN -> registerAdmin(utilisateur,request);
            case CLIENT -> registerClient(utilisateur, request);
            case PRESTATAIRE -> registerPrestataire(utilisateur, request);
            default -> throw new IllegalArgumentException("Rôle inconnu : " + request.getRole());
        }
        return request;
    }

    private void registerAdmin(Utilisateur utilisateur, RegisterRequest request) {
        // Construire l'AdminDto sans EntryKey
        AdminDto adminDto = new AdminDto();
        adminDto.setAdresseMail(request.getEmail());
        adminDto.setNom(request.getUsername());
        adminDto.setEntryKey(request.getEntryKey());
       

        // Appeler le service DAO
        sendToDao("/admins/add", adminDto);
    }

    private void registerClient(Utilisateur utilisateur, RegisterRequest request) {
        ClientDto clientDto = new ClientDto();
        clientDto.setAdresseMail(utilisateur.getEmail());
        clientDto.setNom(utilisateur.getUsername());
        clientDto.setMotDePasse(request.getPassword());
        clientDto.setPrenom(request.getPrenom());
        clientDto.setDateOfBirth(request.getDateOfBirth());
        clientDto.setAdresse(request.getAdresse());
        clientDto.setNumeroCNI(request.getNumeroCNI());

        sendToDao("/clients/add", clientDto);
    }

    private void registerPrestataire(Utilisateur utilisateur, RegisterRequest request) {
        PrestataireDto prestataireDto = new PrestataireDto();
        prestataireDto.setAdresseMail(utilisateur.getEmail());
        prestataireDto.setNom(utilisateur.getUsername());
        prestataireDto.setNatureCompte(request.getNatureCompte());
        prestataireDto.setLatitude(request.getLatitude());
        prestataireDto.setLongitude(request.getLongitude());

        sendToDao("/prestataires/add", prestataireDto);
    }

    private <T> void sendToDao(String uri, T dto) {
        ResponseEntity<Void> response = webClientBuilder.build()
                .post()
                .uri(uri)
                .bodyValue(dto)
                .retrieve()
                .toBodilessEntity()
                .block();

        if (response == null || !response.getStatusCode().is2xxSuccessful()) {
            throw new IllegalStateException("Échec de l'enregistrement dans le service DAO.");
        }
    }

}

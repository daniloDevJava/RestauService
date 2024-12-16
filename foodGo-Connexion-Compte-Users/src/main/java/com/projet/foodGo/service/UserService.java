package com.projet.foodGo.service;

import com.projet.foodGo.exceptions.BusinessException;
import com.projet.foodGo.exceptions.ErrorModel;
import com.projet.foodGo.external.AdminDto;
import com.projet.foodGo.external.ClientDto;
import com.projet.foodGo.external.PrestataireDto;
import com.projet.foodGo.dto.RegisterRequest;
import com.projet.foodGo.model.Utilisateur;
import com.projet.foodGo.model.enumType.RoleUser;
import com.projet.foodGo.repository.UtilisateurRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Optional;

import reactor.core.publisher.Mono; // Pour Mono


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserService {
    private final UtilisateurRepository utilisateurRepository;
    private final ValidationService validationService;
    private final WebClient.Builder webClientBuilder;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Enregistre un utilisateur en fonction de son rôle.
     *
     * @return
     */
    public RegisterRequest registerUser(RegisterRequest request) throws BusinessException {
        List<ErrorModel> errorModelList=new ArrayList<>();
        // Étape 1 : Créer un utilisateur inactif localement
        Optional<Utilisateur> optionalUser=utilisateurRepository.findByEmailAndDeleteAtIsNull(request.getEmail());
        if(request.getRole().equals(RoleUser.CLIENT)) {
            if(LocalDate.now().getYear()-request.getDateOfBirth().getYear()<15){
                ErrorModel errorModel=new ErrorModel();
                errorModel.setCode("NOT_AUTHORIZED");
                errorModel.setMessage("vous n'avez pas l'age minimale pour creer un compte");
                errorModelList.add(errorModel);
                throw new BusinessException(errorModelList);
            }
		}
        else if(optionalUser.isPresent()){
	    ErrorModel errorModel=new ErrorModel();
	    errorModel.setCode("NOT_ALLOWED");
	    errorModel.setMessage("vous essayez d'enregistrer un utilisateur ayant dejà un compte");
	    errorModelList.add(errorModel);
	    throw new BusinessException(errorModelList);
		    
        }
         else if (Objects.equals(request.getPassword(), "")) {
            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode("INVALID_FORMAT");
            errorModel.setMessage("Le mot de pass ne peut etre vide");
            errorModelList.add(errorModel);
            throw new BusinessException(errorModelList);
        }
        else if (!request.getEmail().contains(".") || !request.getEmail().contains("@")) {
            ErrorModel errorModel=new ErrorModel();
            errorModel.setCode("INVALID_FORMAT");
            errorModel.setMessage("L'adresse mail a un format non valide");
            errorModelList.add(errorModel);
            throw new BusinessException(errorModelList);

        }

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setUsername(request.getUsername());
        utilisateur.setEmail(request.getEmail());
        utilisateur.setMdp(bCryptPasswordEncoder.encode(request.getPassword())); // Assurez-vous de hacher le mot de passe avant enregistrement
        utilisateur.setRole(request.getRole());
        utilisateur.setActif(false);

        utilisateur = utilisateurRepository.save(utilisateur);

        // Étape 2 : Générer et envoyer le code de validation
        String code = validationService.generateAndSendValidationCode(utilisateur);

        // Étape 3 : Appeler le DAO en fonction du rôle
        switch (request.getRole()) {
            case ADMIN -> registerAdmin(request);
            case CLIENT -> registerClient(utilisateur, request);
            case VENDEUR -> registerPrestataire(utilisateur, request);
            default -> throw new IllegalArgumentException("Rôle inconnu : " + request.getRole());
        }
        return request;
    }

    private void registerAdmin(RegisterRequest request) {

        AdminDto adminDto = new AdminDto();
        adminDto.setAdresseMail(request.getEmail());
        adminDto.setNom(request.getUsername());
        adminDto.setMotDePasse(request.getPassword());
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
        System.out.println(request.getDateOfBirth());
        clientDto.setDateOfBirth(request.getDateOfBirth());
        clientDto.setAdresse(request.getAdresse());
        clientDto.setNumeroCNI(request.getNumeroCNI());

        sendToDao("/clients/add", clientDto);
    }

    private void registerPrestataire(Utilisateur utilisateur, RegisterRequest request) {
        PrestataireDto prestataireDto = new PrestataireDto();
        prestataireDto.setAdresseMail(utilisateur.getEmail());
        prestataireDto.setMotDePasse(utilisateur.getMdp());
        prestataireDto.setNom(utilisateur.getUsername());
        prestataireDto.setAddress(request.getAdresse());
        prestataireDto.setNatureCompte(request.getNatureCompte());
        prestataireDto.setLatitude(request.getLatitude());
        prestataireDto.setLongitude(request.getLongitude());

        sendToDao("/prestataires/add", prestataireDto);
    }

    private <T> void sendToDao(String uri, T dto) {
	    webClientBuilder.build()
		    .post()
		    .uri(uri)
		    .bodyValue(dto)
		    .retrieve()
		    .onStatus(
		        status -> status.value() >= 400 && status.value() < 500, // Vérifie les erreurs 4xx
		        clientResponse -> clientResponse.bodyToMono(String.class)
		            .flatMap(errorBody -> {
		                ErrorModel errorModel = new ErrorModel();
		                errorModel.setCode("CLIENT_ERROR");
		                errorModel.setMessage("Erreur lors de l'appel au service DAO : " + errorBody);
		                return Mono.error(new BusinessException(List.of(errorModel)));
		            })
		    )
		    .onStatus(
		        status -> status.value() >= 500, // Vérifie les erreurs 5xx
		        clientResponse -> clientResponse.bodyToMono(String.class)
		            .flatMap(errorBody -> {
		                ErrorModel errorModel = new ErrorModel();
		                errorModel.setCode("SERVER_ERROR");
		                errorModel.setMessage("Erreur interne dans le service DAO : " + errorBody);
		                return Mono.error(new BusinessException(List.of(errorModel)));
		            })
		    )
		    .toBodilessEntity()
		    .block();
}


    public RegisterRequest validate(String mail, String code) throws BusinessException {
        Optional<Utilisateur> optionalUtilisateur=utilisateurRepository.findByEmailAndDeleteAtIsNull(mail);
        if(optionalUtilisateur.isPresent()) {
            Utilisateur utilisateur= optionalUtilisateur.get();
            RegisterRequest request = new RegisterRequest();
            request.setUsername(utilisateur.getUsername());
            request.setRole(utilisateur.getRole());
            request.setEmail(utilisateur.getEmail());
            if(validationService.validateCode(mail,code))
                return request;
            else
                return null;

        }
        else
        {
            List<ErrorModel> errorModels=new ArrayList<>();
            ErrorModel errorModel=new ErrorModel();
            errorModel.setCode("INVALID_PARAMETERS");
            errorModel.setMessage("adresse mail incorrecte");
            errorModels.add(errorModel);
            throw new BusinessException(errorModels);
        }


    }
}

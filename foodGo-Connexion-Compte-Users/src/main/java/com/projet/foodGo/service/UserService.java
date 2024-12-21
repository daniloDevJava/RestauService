package com.projet.foodGo.service;

import com.projet.foodGo.exceptions.BusinessException;
import com.projet.foodGo.exceptions.ErrorModel;
import com.projet.foodGo.external.*;
import com.projet.foodGo.dto.RegisterRequest;
import com.projet.foodGo.model.Utilisateur;
import com.projet.foodGo.model.enumType.RoleUser;
import com.projet.foodGo.model.enumType.NatureCompte;
import com.projet.foodGo.repository.JwtRepository;
import com.projet.foodGo.repository.UtilisateurRepository;
import com.projet.foodGo.model.Jwt;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

import reactor.core.publisher.Mono; // Pour Mono


import java.time.LocalDate;

@Service
@AllArgsConstructor
public class UserService {
    private final UtilisateurRepository utilisateurRepository;
    private final ValidationService validationService;
    private final WebClient.Builder webClientBuilder;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final GeolocationService geolocationService;
    private final JwtRepository jwtRepository;

    /**
     * Enregistre un utilisateur en fonction de son rôle.
     *
     *
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


        // Étape 2 : Générer et envoyer le code de validation
        

        // Étape 3 : Appeler le DAO en fonction du rôle
        switch (request.getRole()) {
            case ADMIN -> {
                AdminDto admin=registerAdmin(utilisateur,request);
                request.setEntryKey(admin.getEntryKey());
                utilisateurRepository.save(utilisateur);
                String code = validationService.generateAndSendValidationCode(utilisateur);

            }
            case CLIENT -> {
                registerClient(utilisateur, request);
                utilisateurRepository.save(utilisateur);
                String code = validationService.generateAndSendValidationCode(utilisateur);
            }
            case VENDEUR -> {
                registerPrestataire(utilisateur, request);
                utilisateurRepository.save(utilisateur);
                String code = validationService.generateAndSendValidationCode(utilisateur);
            }
            default -> throw new IllegalArgumentException("Rôle inconnu : " + request.getRole());
        }
        return request;
    }

    private AdminDto registerAdmin(Utilisateur utilisateur,RegisterRequest request) {

        AdminDto adminDto = new AdminDto();
        adminDto.setAdresseMail(request.getEmail());
        adminDto.setNom(request.getUsername());
        adminDto.setMotDePasse(utilisateur.getMdp());
        adminDto.setEntryKey(request.getEntryKey());
        adminDto.setRole(request.getRole());

        // Appeler le service DAO
         return sendToDaoAdmin(adminDto);
    }

    private void registerClient(Utilisateur utilisateur, RegisterRequest request) {
        ClientDto clientDto = new ClientDto();
        clientDto.setAdresseMail(utilisateur.getEmail());
        clientDto.setNom(utilisateur.getUsername());
        clientDto.setMotDePasse(utilisateur.getMdp());
        clientDto.setPrenom(request.getPrenom());
        System.out.println(request.getDateOfBirth());
        clientDto.setDateOfBirth(request.getDateOfBirth());
        clientDto.setAdresse(request.getAdresse());
        clientDto.setNumeroCNI(request.getNumeroCNI());
        clientDto.setRole(request.getRole());

        sendToDao("/clients/add", clientDto);
    }

    private void registerPrestataire(Utilisateur utilisateur, RegisterRequest request) {
        PrestataireDto prestataireDto = new PrestataireDto();
        prestataireDto.setAdresseMail(utilisateur.getEmail());
        prestataireDto.setMotDePasse(utilisateur.getMdp());
        prestataireDto.setNom(utilisateur.getUsername());
        prestataireDto.setAddress(request.getAdresse());
        GeoCoordinates coordinates = geolocationService.getCoordinates(request.getAdresse());
        prestataireDto.setNatureCompte(request.getNatureCompte());
        prestataireDto.setLatitude(Double.valueOf(coordinates.getLatitude()));
        prestataireDto.setLongitude(Double.valueOf(coordinates.getLongitude()));
        prestataireDto.setRole(request.getRole());

        sendToDao("/prestataires/add", prestataireDto);
    }

    private <T>  void sendToDao(String uri, T dto) {
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
                

                ;
}

	private AdminDto sendToDaoAdmin(AdminDto dto) {
	    return webClientBuilder.build()
		    .post()
		    .uri("/admins/add")
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
		    .bodyToMono(AdminDto.class)
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
    /**
     * Met à jour le mot de passe d'un utilisateur.
     *
     * @param email L'email de l'utilisateur.
     * @param newPassword Le nouveau mot de passe.
     * @param oldPassword L'ancien mot de passe.
     */
    public void updatePassword(String email, String newPassword, String oldPassword) throws BusinessException{
        Optional<Utilisateur> optionalUtilisateur=utilisateurRepository.findByEmailAndDeleteAtIsNull(email);
        if(optionalUtilisateur.isPresent()) {
                Utilisateur utilisateur= optionalUtilisateur.get();
		// Étape 1 : Trouver l'utilisateur correspondant à l'email
		UserDto userDto = getUserByEmail(email);
		if(utilisateur.getEmail().equals(userDto.getMotDePasse()))
			System.out.println("Ok");
		// Étape 2 : Vérifier l'ancien mot de passe
		if (!bCryptPasswordEncoder.matches(oldPassword, userDto.getMotDePasse())) {
		    throw new IllegalArgumentException("L'ancien mot de passe est incorrect.");
		}

		// Étape 3 : Encoder le nouveau mot de passe
		String encodedPassword = bCryptPasswordEncoder.encode(newPassword);

		// Étape 4 : Mettre à jour le mot de passe dans le service DAO
		updatePasswordInDao(userDto.getId(), encodedPassword,userDto.getMotDePasse());
		utilisateur.setMdp(encodedPassword);
		utilisateurRepository.save(utilisateur);
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

    /**
     * Parcourt la liste des utilisateurs pour trouver celui correspondant à l'email.
     *
     * @param email L'email de l'utilisateur.
     * @return Le DTO utilisateur contenant l'ID et le mot de passe.
     */
    private UserDto getUserByEmail(String email) {
        String url = "/users/all";

        List<UserDto> users = webClientBuilder.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToFlux(UserDto.class)
                .collectList()
                .block();

        assert users != null;
        return users.stream()
                .filter(user -> user.getAdresseMail().equalsIgnoreCase(email))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable avec l'email : " + email));
    }

    /**
     * Met à jour le mot de passe d'un utilisateur dans le service DAO.
     *
     * @param id L'ID de l'utilisateur dans le service DAO.
     * @param encodedPassword Le nouveau mot de passe encodé.
     */
    private void updatePasswordInDao(UUID id, String encodedPassword,String oldPassword) {
        String url = String.format("/users/%s/update-password?oldPassWord=%s", id,oldPassword);
        UserDto updatedUserDto = new UserDto();
        updatedUserDto.setMotDePasse(encodedPassword);

        webClientBuilder.build()
                .patch()
                .uri(url)
                .bodyValue(updatedUserDto)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        response -> response.createException()
                                .map(ex -> new IllegalArgumentException("Erreur lors de la mise à jour du mot de passe."))
                )
                .toBodilessEntity()
                .block();
    }
    /**
     * Restreindre un prestataire.
     *
     * @param email L'email du prestataire à restreindre.
     * @throws BusinessException Si le prestataire n'existe pas ou en cas d'erreur.
     */

    public void restrictPrestataire(String email, String mailAdmin) throws BusinessException {
    
        Optional<Utilisateur> optionalAdmin=utilisateurRepository.findByEmailAndDeleteAtIsNull(mailAdmin);
        Optional<Utilisateur> optionalPrestataire=utilisateurRepository.findByEmailAndDeleteAtIsNull(email);

        if(optionalPrestataire.isPresent() && optionalAdmin.isPresent()){
            Utilisateur Admin=optionalAdmin.get();
            Utilisateur prestataire= optionalPrestataire.get();
            if(Admin.getRole() != RoleUser.ADMIN){
                ErrorModel errorModel=new ErrorModel();
                errorModel.setCode("AUTHORIZATION_FAILED");
                errorModel.setMessage("seul un administrateur est capable d'effectuer cette operation");
                throw new BusinessException(List.of(errorModel));
            } else if (prestataire.getRole() == RoleUser.VENDEUR) {
		// Étape 1 : Invalider tous les JWT et RefreshTokens associés
		invalidateTokensForUser(email);

		// Étape 2 : Changer la nature du compte dans le service DAO
		updatePrestataireNatureInDao(email);

            }

        }
    }
    /**
     * Invalider tous les JWT et RefreshTokens liés à un utilisateur.
     *
     * @param email L'email de l'utilisateur.
     */
    private void invalidateTokensForUser(String email) throws BusinessException{
        List<Jwt> jwts = jwtRepository.findByUtilisateurEmail(email);

        if (jwts.isEmpty()) {
            ErrorModel errorModel=new ErrorModel();
                errorModel.setCode("AUTHORIZATION_FAILED");
                errorModel.setMessage("Aucun token actif pour cet utilisateur.");
                throw new BusinessException(List.of(errorModel));
         
        }

        jwts.forEach(jwt -> {
            jwt.setExpire(true);
            jwt.setDesactive(true);
            jwt.getRefreshToken().setExpire(true);
            jwtRepository.save(jwt);
        });
    }
    private void validateTokensForUser(String email) throws BusinessException{
        List<Jwt> jwts = jwtRepository.findByUtilisateurEmail(email);

        if (jwts.isEmpty()) {
            ErrorModel errorModel=new ErrorModel();
            errorModel.setCode("AUTHORIZATION_FAILED");
            errorModel.setMessage("Aucun token actif pour cet utilisateur.");
            throw new BusinessException(List.of(errorModel));

        }

        jwts.forEach(jwt -> {
            jwt.setExpire(false);
            jwt.setDesactive(false);
            jwt.getRefreshToken().setExpire(false);
            jwtRepository.save(jwt);
        });
    }
    
     /**
      * Changer la nature du compte du prestataire dans le service DAO.
      *
      * @param email L'email du prestataire.
      */
    private void updatePrestataireNatureInDao(String email) {
		String url = String.format("/prestataires/%s/changer-natureCompte", email);

		PrestataireDto prestataireDto = new PrestataireDto();
		prestataireDto.setNatureCompte(NatureCompte.RESTREINT);

		webClientBuilder.build()
		        .patch()
		        .uri(url)
		        .bodyValue(prestataireDto)
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

    public void unrestrictedPrestataire(String emailPrestataire, String mailAdmin, PrestataireDto prestataireDto) throws BusinessException {
        Optional<Utilisateur> optionalAdmin=utilisateurRepository.findByEmailAndDeleteAtIsNull(mailAdmin);
        Optional<Utilisateur> optionalPrestataire=utilisateurRepository.findByEmailAndDeleteAtIsNull(emailPrestataire);

        if(optionalPrestataire.isPresent() && optionalAdmin.isPresent()){
            Utilisateur Admin=optionalAdmin.get();
            Utilisateur prestataire= optionalPrestataire.get();
            if(Admin.getRole() != RoleUser.ADMIN){
                ErrorModel errorModel=new ErrorModel();
                errorModel.setCode("AUTHORIZATION_FAILED");
                errorModel.setMessage("seul un administrateur est capable d'effectuer cette operation");
                throw new BusinessException(List.of(errorModel));
            } else if (prestataire.getRole() == RoleUser.VENDEUR) {
                validateTokensForUser(emailPrestataire);
                String url = String.format("/prestataires/%s/changer-natureCompte", emailPrestataire);
                webClientBuilder.build()
                        .patch()
                        .uri(url)
                        .bodyValue(prestataireDto)
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

            }

    }
}

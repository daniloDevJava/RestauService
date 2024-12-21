package com.projet.foodGo.service;

import com.projet.foodGo.dto.AccessTokenDto;
import com.projet.foodGo.dto.AuthentificationDto;
import com.projet.foodGo.exceptions.BusinessException;
import com.projet.foodGo.exceptions.ErrorModel;
import com.projet.foodGo.model.Jwt;
import com.projet.foodGo.model.RefreshToken;
import com.projet.foodGo.model.Utilisateur;
import com.projet.foodGo.model.enumType.RoleUser;
import com.projet.foodGo.repository.JwtRepository;
import com.projet.foodGo.repository.RefreshTokenRepository;
import com.projet.foodGo.repository.UtilisateurRepository;
import com.projet.foodGo.util.JwtUtil;
import com.projet.foodGo.model.enumType.NatureCompte;
import com.projet.foodGo.external.PrestataireDto;
import com.projet.foodGo.external.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class JwtService {

    private final JwtRepository jwtRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final WebClient.Builder webClientBuilder;
    private final JwtUtil jwtUtil;
    private final UtilisateurRepository utilisateurRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private static final double MONTANT_DEDUCTION = 20000;

    public AuthentificationDto generateTokens(Utilisateur utilisateur) {
        long refreshTokenValidity = getRefreshTokenValidityInSeconds(utilisateur.getRole());
        getAccessTokenValidityInSeconds();

        String accessToken = jwtUtil.generateAccessToken(utilisateur.getEmail(), utilisateur.getRole().name());
        String refreshTokenValue = UUID.randomUUID().toString();

        RefreshToken refreshToken = RefreshToken.builder()
                .valeur(refreshTokenValue)
                .creation(Instant.now())
                .expiration(Instant.now().plusSeconds(refreshTokenValidity))
                .expire(false)
                .build();
        refreshToken = refreshTokenRepository.save(refreshToken);

        Jwt jwt = Jwt.builder()
                .valeur(accessToken)
                .expire(false)
                .desactive(false)
                .refreshToken(refreshToken)
                .utilisateur(utilisateur)
                .build();
        jwtRepository.save(jwt);

        return new AuthentificationDto(accessToken, refreshTokenValue);
    }

    public AuthentificationDto login(String email, String password) throws BusinessException {

	    Optional<Utilisateur> optionalUser = utilisateurRepository.findByEmailAndDeleteAtIsNull(email);

	    if (optionalUser.isPresent()) {
		Utilisateur utilisateur = optionalUser.get();

		// Vérification du mot de passe
		if (!passwordEncoder.matches(password, utilisateur.getPassword())) {
		    ErrorModel errorModel = new ErrorModel();
		    errorModel.setCode("AUTHENTIFICATION FAILED");
		    errorModel.setMessage("Identifiants invalides");
		    throw new BusinessException(List.of(errorModel));
		}
		
		

		// Vérification de la nature du compte pour les prestataires
		if (utilisateur.getRole() == RoleUser.VENDEUR ) {
		    PrestataireDto prestataire=getPrestataireByName(utilisateur.getUsername());
		    if(prestataire.getNatureCompte()==NatureCompte.RESTREINT){
			    ErrorModel errorModel = new ErrorModel();
			    errorModel.setCode("ACCOUNT RESTRICTED");
			    errorModel.setMessage("Votre compte est restreint, veuillez contacter l'administrateur.");
			    throw new BusinessException(List.of(errorModel));
		    }
		}

		// Générer les tokens si tout est valide
		return generateTokens(utilisateur);
	    } else {
		ErrorModel errorModel = new ErrorModel();
		errorModel.setCode("AUTHENTIFICATION FAILED");
		errorModel.setMessage("Utilisateur non trouvé");
		throw new BusinessException(List.of(errorModel));
	    }
}

    public AuthentificationDto refreshTokens(String refreshTokenValue) throws BusinessException {

        Optional<RefreshToken> optionalrefreshToken = refreshTokenRepository.findByValeurAndExpireFalse(refreshTokenValue);
        if (optionalrefreshToken.isPresent()) {
            RefreshToken refreshToken = optionalrefreshToken.get();
            if (refreshToken.getExpiration().isBefore(Instant.now())) {
                Optional<Jwt> optionalJwt = jwtRepository.findByRefreshToken(refreshToken);
                if (optionalJwt.isPresent()) {
                    Utilisateur utilisateur = optionalJwt.get().getUtilisateur();
                    if (utilisateur == null) {
                        ErrorModel errorModel = new ErrorModel();
                        errorModel.setCode("FAILED_AUTHENTIFICATION");
                        errorModel.setMessage("Aucun utilisateur associé à ce RefreshToken");
                        throw new BusinessException(List.of(errorModel));
                    } else if (utilisateur.getRole() == RoleUser.VENDEUR) {
                        PrestataireDto prestataire = getPrestataireByName(utilisateur.getUsername());
                        System.out.println(prestataire.getNom() + prestataire.getNatureCompte());
                        switch (prestataire.getNatureCompte()) {
                            case STANDARD -> {
                                if (prestataire.getMontantCompte() < MONTANT_DEDUCTION) {
                                    List<ErrorModel> errorModelList = new ArrayList<>();
                                    ErrorModel errorModel = new ErrorModel();
                                    errorModel.setCode("INVALID_ENTRY");
                                    errorModel.setMessage("montant du compte insuffisant pour prelever");
                                    errorModelList.add(errorModel);
                                    throw new BusinessException(errorModelList);
                                }
                                updateMontantCompte(prestataire.getId(), prestataire.getMontantCompte() - MONTANT_DEDUCTION);
                            }
                            case VIP -> {
                                if (prestataire.getMontantCompte() < (MONTANT_DEDUCTION + 10000)) {
                                    List<ErrorModel> errorModelList = new ArrayList<>();
                                    ErrorModel errorModel = new ErrorModel();
                                    errorModel.setCode("INVALID_ENTRY");
                                    errorModel.setMessage("montant du compte insuffisant pour prelever");
                                    errorModelList.add(errorModel);
                                    throw new BusinessException(errorModelList);
                                }
                                updateMontantCompte(prestataire.getId(), prestataire.getMontantCompte() - MONTANT_DEDUCTION - 10000);
                            }
                            case RESTREINT -> {
                                ErrorModel errorModel = new ErrorModel();
                                errorModel.setCode("AUTHORIZATION_FAILED");
                                errorModel.setMessage("votre compte a été restreint par un admininstrateur de la plateforme veuillez en contacter un par mail pour lever les restrictions");
                                throw new BusinessException(List.of(errorModel));
                            }
                        }


                    }
                    refreshToken.setExpire(true);
                    refreshTokenRepository.save(refreshToken);
                    return generateTokens(utilisateur);

                }
            }
            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode("INVALID_ENTRY");
            errorModel.setMessage("refresh token pas encore expiré");
            throw new BusinessException(List.of(errorModel));


        }
        ErrorModel errorModel = new ErrorModel();
        errorModel.setCode("INVALID_ENTRY");;
        errorModel.setMessage("refresh token inconnu");
        throw new BusinessException(List.of(errorModel));


    }

    public void invalidateRefreshToken(String refreshTokenValue) throws BusinessException {
        Optional<RefreshToken> optionalrefreshToken = refreshTokenRepository.findByValeur(refreshTokenValue);
        if(optionalrefreshToken.isPresent()) {
            RefreshToken refreshToken=optionalrefreshToken.get();
            refreshToken.setExpire(true);
            refreshTokenRepository.save(refreshToken);
        }
        else{
            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode("INVALID_ENTRY");
            errorModel.setMessage("refresh token inconnu");
            throw new BusinessException(List.of(errorModel));
        }
    }

    public long getRefreshTokenValidityInSeconds(RoleUser role) {
        return switch (role) {
            case CLIENT -> TimeUnit.DAYS.toSeconds(15);
            case VENDEUR -> TimeUnit.DAYS.toSeconds(30);
            case ADMIN -> TimeUnit.DAYS.toSeconds(20);
        };
    }

    public long getAccessTokenValidityInSeconds() {
        return TimeUnit.MINUTES.toSeconds(15);
    }

    private PrestataireDto getPrestataireByName(String username) {
        String uri = "/prestataires/" + username + "/get-by-name";
        ErrorModel errorModel = new ErrorModel();
        errorModel.setCode("BAD_INFORMATIONS");
        errorModel.setMessage("prestataire non trouvé");
        return webClientBuilder.build()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(PrestataireDto.class)
                .onErrorMap(ex ->
                        new BusinessException(List.of(errorModel)))
                .block();
    }

    private void updateMontantCompte(UUID id, double newMontant) {
        String uri = "/users/{id}/update-montant";
        UserDto userDto = new UserDto();
        userDto.setMontantCompte(newMontant);
        webClientBuilder.build()
                .patch()
                .uri(uri, id)
                .bodyValue(userDto)
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

    public AccessTokenDto refreshAccessToken(String refreshTokenValue) throws BusinessException {


        Optional<RefreshToken> optionalrefreshToken = refreshTokenRepository.findByValeurAndExpireFalse(refreshTokenValue);

        if (optionalrefreshToken.isPresent()) {
            RefreshToken refreshToken = optionalrefreshToken.get();
            if (!refreshToken.getExpiration().isBefore(Instant.now())) {
                Optional<Jwt> optionalJwt = jwtRepository.findByRefreshToken(refreshToken);
                if (optionalJwt.isPresent()) {
                    Utilisateur utilisateur = optionalJwt.get().getUtilisateur();
                    if (utilisateur == null) {
                        ErrorModel errorModel = new ErrorModel();
                        errorModel.setCode("FAILED_AUTHENTIFICATION");
                        errorModel.setMessage("Aucun utilisateur associé à ce RefreshToken");
                        throw new BusinessException(List.of(errorModel));
                    }
                    String accessToken = jwtUtil.generateAccessToken(utilisateur.getEmail(), utilisateur.getRole().name());
                    return new AccessTokenDto(accessToken);
                }
            }
            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode("INVALID_ENTRY");
            errorModel.setMessage("refresh token expiré");
            throw new BusinessException(List.of(errorModel));

        }
        ErrorModel errorModel = new ErrorModel();
        errorModel.setCode("INVALID_ENTRY");
        errorModel.setMessage("refresh token inconnu");
        throw new BusinessException(List.of(errorModel));
    }
}

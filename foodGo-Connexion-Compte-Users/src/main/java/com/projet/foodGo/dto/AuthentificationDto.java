package com.projet.foodGo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthentificationDto {
    String accessToken;
    String refreshToken;
}

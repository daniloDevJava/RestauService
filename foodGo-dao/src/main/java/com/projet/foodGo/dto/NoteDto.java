package com.projet.foodGo.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public class NoteDto {

    private UUID id;
    private String avis;
    private int note;
    private UserDto userDto;
    private PrestataireDto prestataireDto;
}

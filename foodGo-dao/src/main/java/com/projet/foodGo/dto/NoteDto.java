package com.projet.foodGo.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NoteDto {

    private UUID id;
    @NotEmpty(message = "The avis is not empty. You can give a note without avis but don't show and empty advice")
    private String avis;
    @NotNull(message = "Note is mandatory")
    private int note;
    private UserDto userDto;
    private PrestataireDto prestataireDto;
}

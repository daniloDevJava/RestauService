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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAvis() {
        return avis;
    }

    public void setAvis(String avis) {
        this.avis = avis;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public PrestataireDto getPrestataireDto() {
        return prestataireDto;
    }

    public void setPrestataireDto(PrestataireDto prestataireDto) {
        this.prestataireDto = prestataireDto;
    }


}

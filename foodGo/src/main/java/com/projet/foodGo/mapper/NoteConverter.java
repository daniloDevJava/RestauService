package com.projet.foodGo.mapper;

import com.projet.foodGo.dto.NoteDto;
import com.projet.foodGo.dto.PrestataireDto;
import com.projet.foodGo.model.Note;
import com.projet.foodGo.model.Prestataire;
import org.springframework.stereotype.Component;

@Component
public class NoteConverter {
    private final UserConverter userConverter;
    private final PrestataireConverter prestataireConverter;

    public NoteConverter(UserConverter userConverter, PrestataireConverter prestataireConverter) {
        this.userConverter = userConverter;
        this.prestataireConverter = prestataireConverter;
    }


    public Note toEntity(NoteDto noteDto){
        if(noteDto==null)
            return null;
        Note note=new Note();
        note.setNote(noteDto.getNote());
        note.setAvis(noteDto.getAvis());
        note.setUser(userConverter.toEntity(noteDto.getUserDto()));
        note.setPrestataire(prestataireConverter.toEntity(noteDto.getPrestataireDto()));
        return note;
    }
    public NoteDto toDto(Note note){
        if(note==null)
            return null;
        NoteDto noteDto=new NoteDto();
        noteDto.setAvis(note.getAvis());
        noteDto.setId(note.getId());
        noteDto.setNote(note.getNote());
        noteDto.setUserDto(userConverter.toDto(note.getUser()));
        noteDto.setPrestataireDto(prestataireConverter.toDto(note.getPrestataire()));
        return noteDto;
    }
}

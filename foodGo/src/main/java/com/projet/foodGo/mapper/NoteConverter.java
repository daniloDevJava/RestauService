package com.projet.foodGo.mapper;

import com.projet.foodGo.dto.NoteDto;
import com.projet.foodGo.model.Note;
import com.projet.foodGo.model.Prestataire;
import com.projet.foodGo.repository.PrestataireRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class NoteConverter {
    private final UserConverter userConverter;
    private final PrestataireConverter prestataireConverter;
    private final PrestataireRepository prestataireRepository;


    public Note toEntity(NoteDto noteDto){
        if(noteDto==null)
            return null;
        Note note=new Note();
        note.setNote(noteDto.getNote());
        note.setAvis(noteDto.getAvis());

        return note;
    }
    public NoteDto toDto(Note note){
        if(note==null)
            return null;
        Optional<Prestataire> optionalPrestataire=prestataireRepository.findByIdAndDeleteAtIsNull(note.getPrestataire().getId());
        NoteDto noteDto=new NoteDto();
        noteDto.setAvis(note.getAvis());
        noteDto.setId(note.getId());
        noteDto.setNote(note.getNote());
        noteDto.setUserDto(userConverter.toDto(note.getUser()));
        optionalPrestataire.ifPresent(prestataire -> noteDto.setPrestataireDto(prestataireConverter.toDto(prestataire)));

        return noteDto;
    }
}

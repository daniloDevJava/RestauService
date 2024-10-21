package com.projet.foodGo.service;

import com.projet.foodGo.dto.NoteDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface NoteService {
    NoteDto createNote(NoteDto noteDto);
    NoteDto getNote(UUID id);
    List<NoteDto> getNotes();
    List<NoteDto> getNotes(UUID prestataire_id);
    List<NoteDto> getNotesByOtherUser(UUID user_id);
    NoteDto updateNote(UUID id,NoteDto noteDto);
    boolean deleteNote(UUID id);
}

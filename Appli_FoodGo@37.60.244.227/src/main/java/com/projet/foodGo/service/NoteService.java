package com.projet.foodGo.service;

import com.projet.foodGo.dto.NoteDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * The interface Note service.
 */
@Service
public interface NoteService {
    /**
     * Create  note dto.
     *
     * @param noteDto       the note dto
     * @param userId        the user id
     * @param prestataireId the prestataire id
     * @return the note dto
     */
    NoteDto createNote(NoteDto noteDto, UUID userId, UUID prestataireId);

    /**
     * Gets note.
     *
     * @param id the id
     * @return the note
     */
    NoteDto getNote(UUID id);

    /**
     * Gets notes.
     *
     * @param prestataire_id the prestataire id
     * @return the notes
     */
    List<NoteDto> getNotes(UUID prestataire_id);

    /**
     * Gets notes by user.
     *
     * @param user_id the user id
     * @return the notes by user
     */
    List<NoteDto> getNotesByUser(UUID user_id);

    /**
     * Update avis note dto.
     *
     * @param id      the id
     * @param noteDto the note dto
     * @return the note dto
     */
    NoteDto updateAvis(UUID id, NoteDto noteDto);
}

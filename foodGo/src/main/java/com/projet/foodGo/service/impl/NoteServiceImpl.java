package com.projet.foodGo.service.impl;

import com.projet.foodGo.dto.NoteDto;
import com.projet.foodGo.mapper.NoteConverter;
import com.projet.foodGo.model.Note;
import com.projet.foodGo.model.Prestataire;
import com.projet.foodGo.model.Users;
import com.projet.foodGo.repository.NoteRepository;
import com.projet.foodGo.repository.PrestataireRepository;
import com.projet.foodGo.repository.UserRepository;
import com.projet.foodGo.service.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The type Note service.
 */
@Service
@AllArgsConstructor
public class NoteServiceImpl implements NoteService {
    private final UserRepository userRepository;
    private final PrestataireRepository prestataireRepository;
    private final NoteConverter noteConverter;
    private final NoteRepository noteRepository;


    @Override
    public NoteDto createNote(NoteDto noteDto, UUID userId, UUID prestataireId) {
        Optional<Users> optionalUsers=userRepository.findByIdAndDeleteAtIsNull(userId);
        Optional<Prestataire> optionalPrestataire=prestataireRepository.findByIdAndDeleteAtIsNull(prestataireId);
        if(optionalPrestataire.isPresent() && optionalUsers.isPresent()){
            Users users=optionalUsers.get();
            Prestataire prestataire=optionalPrestataire.get();
            if(users instanceof Prestataire)
                throw new IllegalArgumentException("Un prestataire ne peut en noter un autre");
            else{
                Note note=noteConverter.toEntity(noteDto);
                note.setUser(users);
                note.setPrestataire(prestataire);
                note=noteRepository.save(note);
                double noteMoyenne=noteRepository.calculNoteMoyen(note.getPrestataire().getId());
                prestataire.setNoteMoyenne(noteMoyenne);
                prestataireRepository.save(prestataire);
                return noteConverter.toDto(note);
            }
        }
        throw new IllegalArgumentException("Prestataire ou user non trouvé");
    }

    @Override
    public NoteDto getNote(UUID id) {
        Optional<Note> optionalNote=noteRepository.findByIdAndDeleteAtIsNull(id);
        return optionalNote.map(noteConverter::toDto).orElse(null);
    }

    @Override
    public List<NoteDto> getNotes(UUID prestataire_id) {
        Optional<Prestataire> optionalPrestataire=prestataireRepository.findByIdAndDeleteAtIsNull(prestataire_id);
        if(optionalPrestataire.isPresent()) {
            List<Note> noteList = noteRepository.findByPrestataireAndDeleteAtIsNull(optionalPrestataire.get());
            return noteList.stream()
                    .map(noteConverter::toDto)
                    .collect(Collectors.toList());
        }
        throw new IllegalArgumentException("Prestataire non trouvé");
    }

    @Override
    public List<NoteDto> getNotesByUser(UUID user_id) {
        Optional<Users> optionalUsers=userRepository.findByIdAndDeleteAtIsNull(user_id);
        if(optionalUsers.isPresent()){
            List<Note> noteList=noteRepository.findByUserAndDeleteAtIsNull(optionalUsers.get());
            return noteList.stream()
                    .map(noteConverter::toDto)
                    .collect(Collectors.toList());
        }
        throw new IllegalArgumentException("Utilisateur non trouvé");
    }

    @Override
    public NoteDto updateAvis(UUID id, NoteDto noteDto) {
        Optional<Note> optionalNote=noteRepository.findByIdAndDeleteAtIsNull(id);
        if(optionalNote.isPresent()){
            Note note= optionalNote.get();
            note.setAvis(noteDto.getAvis());
            return noteConverter.toDto(noteRepository.save(note));
        }
        return null;
    }
}

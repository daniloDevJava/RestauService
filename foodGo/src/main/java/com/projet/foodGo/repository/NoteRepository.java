package com.projet.foodGo.repository;

import com.projet.foodGo.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NoteRepository extends JpaRepository<Note, UUID> {

    Optional<Note> findByIdAndDeleteAtIsNull(UUID uuid);
    List<Note> findByDeleteAtIsNull();
}

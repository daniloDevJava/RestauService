package com.projet.foodGo.controller;

import com.projet.foodGo.dto.NoteDto;
import com.projet.foodGo.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/notes")
@AllArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @PostMapping("/add")
    @Operation(summary = "add note")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "the note is added successfully"),
            @ApiResponse(responseCode = "500",description = "prestataire or user is not found"),
            @ApiResponse(responseCode = "500",description = "Un prestataire essaie d'en noter un autre")
    })
    public ResponseEntity<NoteDto> createNote(@Parameter(description = "Id Of User") @RequestParam("userId")UUID userId, @Parameter(description = "Id Of Prestataire") @RequestParam("prestataireId") UUID prestataireId, @RequestBody NoteDto noteDto){
        try {
            NoteDto note= noteService.createNote(noteDto,userId,prestataireId);
            return new ResponseEntity<>(note,HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            NoteDto noteDto1=new NoteDto();
            noteDto1.setAvis("Un prestataire ne peut en noter un autre");
            return new ResponseEntity<>(noteDto1,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "get an note")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "the found not"),
            @ApiResponse(responseCode = "404",description = "note doesn't exists")
    })
    public ResponseEntity<NoteDto> getNote(@Parameter(description = "ID OF Note") @PathVariable UUID id){
        NoteDto note= noteService.getNote(id);
        if(note!=null)
            return new ResponseEntity<>(note,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/list-of-an-user")
    @Operation(summary = "get list of notes by user has given")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "the found list"),
            @ApiResponse(responseCode = "500",description = "user is not found")
    })
    public ResponseEntity<List<NoteDto>> getAllByUser(@Parameter(description = "Id Of User") @RequestParam("userId")UUID userId){
        try {
            List<NoteDto> noteDtoList = noteService.getNotesByUser(userId);
            return new ResponseEntity<>(noteDtoList,HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/list-of-an-prestataire")
    @Operation(summary = "get list of notes for prestataire has given")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "the found list"),
            @ApiResponse(responseCode = "500",description = "user is not found")
    })
    public ResponseEntity<List<NoteDto>> getAllForPrestataire(@Parameter(description = "Id Of User") @RequestParam("userId")UUID prestataireId){
        try {
            List<NoteDto> noteDtoList = noteService.getNotes(prestataireId);
            return new ResponseEntity<>(noteDtoList,HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/{id}")
    @Operation(summary = "changer l'avis d'une note")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "the avis is up to date"),
            @ApiResponse(responseCode = "404",description = "note is not found")
    })
    public ResponseEntity<NoteDto> updateNote(@Parameter(description = "Id of Note") UUID id,@RequestBody NoteDto noteDto){
        NoteDto note=noteService.updateAvis(id,noteDto);
        if(note!=null)
            return new ResponseEntity<>(note,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}

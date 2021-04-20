package com.axa.softwareacademy.notes.controller;

import com.axa.softwareacademy.notes.domain.Note;
import com.axa.softwareacademy.notes.request.NoteSaveRequest;
import com.axa.softwareacademy.notes.service.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("notes")
@RequiredArgsConstructor
@CrossOrigin
public class NoteController {

    private final NoteService noteService;

    /**
     * This endpoint is aimed to find note information
     * @param id is the unique id number of the note
     * @return the note
     */
    @GetMapping("id")
    public Note getNoteById(@RequestParam String id) throws Exception {
        Optional<Note> result = noteService.findById(id);
        if (result.isEmpty()) {
            log.debug("Find user by id failed");
            throw new Exception("4O4");
        }

        Note note = result.get();
        log.debug(note.toString());
        return note;
    }

    /**
     * This endpoint is aimed to find all note of a patient
     * @param id is the unique id number of the patient
     * @return a list of notes
     */
    @GetMapping("patientId")
    public List<Note> getNoteByPatientId(@RequestParam String id) {
        return noteService.findByPatientId(id);
    }

    /**
     * This endpoint is aimed to find all notes present in database
     * @return a list of all notes
     */
    @GetMapping
    public List<Note> noteList() {
        log.debug("Start finding all notes");
        return noteService.findAll();
    }

    /**
     * This endpoint is aimed to add a note to database
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addNote(@RequestBody @Valid NoteSaveRequest noteSaveRequest) {
        log.debug("Start saving note");
        noteService.save(noteSaveRequest);
    }

    /**
     * This endpoint is aimed to modify a note present in database
     * @param noteSaveRequest is a valid note request
     * @param id is the unique id number of the note to modify
     * @throws Exception when note to modify is not found
     */
    @PutMapping
    public void modifyNote(@RequestBody @Valid NoteSaveRequest noteSaveRequest, @RequestParam String id) throws Exception {
        noteService.update(noteSaveRequest, id);
    }

    /**
     * This endpoint is aimed to delete a note present in database
     * @param id is the unique id number of the note to delete
     * @throws Exception when note to delete is not found
     */
    @DeleteMapping
    public void deleteNote(@RequestParam String id) throws Exception {
        noteService.delete(id);
    }
}

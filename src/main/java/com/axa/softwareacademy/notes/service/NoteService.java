package com.axa.softwareacademy.notes.service;

import com.axa.softwareacademy.notes.domain.Note;
import com.axa.softwareacademy.notes.repository.NoteRepository;
import com.axa.softwareacademy.notes.request.NoteSaveRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    public Optional<Note> findById(String id) {
        return noteRepository.findById(id);
    }

    public List<Note> findByPatientId(String patientId) {
        return noteRepository.findNoteByPatientId(patientId);
    }

    public List<Note> findAll() {
        return noteRepository.findAll();
    }

    public void update(NoteSaveRequest noteSaveRequest, String id) throws Exception {
        Optional<Note> result = noteRepository.findById(id);
        if (result.isEmpty()) {
            log.debug("Find user by id failed");
            throw new Exception("404");
        }
        Note note = convertToNote(noteSaveRequest);
        note.setNoteId(id);
        noteRepository.save(note);
    }

    public void save(NoteSaveRequest noteSaveRequest) {
        noteRepository.save(convertToNote(noteSaveRequest));
    }

    public void delete(String id) throws Exception {
        Optional<Note> result = noteRepository.findById(id);
        if (result.isEmpty()) {
            log.debug("Find user by id failed");
            throw new Exception("404");
        }
        noteRepository.deleteById(id);
    }

    private Note convertToNote(NoteSaveRequest noteSaveRequest) {
        Note note = new Note();
        note.setNoteDetail(noteSaveRequest.getNoteDetail());
        note.setPatientId(noteSaveRequest.getPatientId());
        return note;
    }

}

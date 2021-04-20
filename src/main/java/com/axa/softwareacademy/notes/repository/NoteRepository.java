package com.axa.softwareacademy.notes.repository;

import com.axa.softwareacademy.notes.domain.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NoteRepository extends MongoRepository<Note, String> {

    List<Note> findNoteByPatientId(String patientId);
}

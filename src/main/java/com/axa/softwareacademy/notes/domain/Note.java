package com.axa.softwareacademy.notes.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Note {

    @Id
    private String noteId;
    private String noteDetail;
    private String patientId;
}

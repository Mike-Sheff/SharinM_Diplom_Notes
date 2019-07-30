package ru.netologia.sharinm_diplom_notes;

import java.util.List;

public interface NoteRepository {

    Note getNoteById(String id);

    List<Note> getNotes();

    boolean connection();

    void createDefaultNotes();

    void saveNote(Note note);

    void deleteById(String id);
}

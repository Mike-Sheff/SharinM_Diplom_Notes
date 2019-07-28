package ru.netologia.sharinm_diplom_notes;

import android.content.Context;

import java.util.List;

public interface NoteRepository {

    Note getNoteById(String id, Context context, String fileName);

    List<Note> getNotes(Context context, String fileName);

    void saveNote(Note note, Context context, String fileName);

    void deleteById(String id, Context context, String fileName);
}

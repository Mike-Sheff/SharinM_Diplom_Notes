package ru.netologia.sharinm_diplom_notes;

import android.content.Context;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;

public class FileNoteRepository implements NoteRepository {

    private Context context;
    private String fileName;

    FileNoteRepository(Context context, String fileName) {
        this.context = context;
        this.fileName = fileName;
    }

    @Override
    public boolean connection() {
        File file = context.getFileStreamPath(fileName);
        if (file == null || !file.exists()) {
            return false;
        }
        return true;
    }

    @Override
    public void createDefaultNotes() {
        // Есть ли смысл это все закидывать в defaultNotes. Если это все тестовое? или создать отдельный файл?
        String dateNow = new SimpleDateFormat(context.getString(R.string.formatDate), Locale.getDefault()).format(new Date());

        saveNote(new Note(context.getString(R.string.headline_1), context.getString(R.string.textNote_1), context.getString(R.string.dateDeadline_1), dateNow));
        saveNote(new Note(null, context.getString(R.string.textNote_2), null, context.getString(R.string.dateUpdate_2)));
        saveNote(new Note(null, context.getString(R.string.textNote_3), null, context.getString(R.string.dateUpdate_3)));
        saveNote(new Note(context.getString(R.string.headline_4), context.getString(R.string.textNote_4), null, context.getString(R.string.dateUpdate_4)));
        saveNote(new Note(null, context.getString(R.string.textNote_5), context.getString(R.string.dateDeadline_5), context.getString(R.string.dateUpdate_5)));
        saveNote(new Note(null, context.getString(R.string.textNote_6), context.getString(R.string.dateDeadline_6), dateNow));
        saveNote(new Note(context.getString(R.string.headline_7), context.getString(R.string.textNote_7), context.getString(R.string.dateDeadline_7), dateNow));
        saveNote(new Note(context.getString(R.string.headline_8), context.getString(R.string.textNote_8), context.getString(R.string.dateDeadline_8), context.getString(R.string.dateUpdate_8)));
        saveNote(new Note(null, context.getString(R.string.textNote_9), null, dateNow));
        saveNote(new Note(context.getString(R.string.headline_10), context.getString(R.string.textNote_10), context.getString(R.string.dateDeadline_10), context.getString(R.string.dateUpdate_10)));
    }

    @Override
    public Note getNoteById(String id) {
        List<Note> notes = getNotes();
        return notes.get(Integer.parseInt(id));
    }

    @Override
    public List<Note> getNotes() {

        List<Note> noteList = new ArrayList<>();
        BufferedReader br = null;
        try {
            // открываем поток для чтения
            br = new BufferedReader(new InputStreamReader(
                    context.openFileInput(fileName)));
            String str;

            // читаем содержимое
            while ((str = br.readLine()) != null) {

                String[] arrayContent = str.split("\n");

                for (int i = 0; i < arrayContent.length; i++) {
                    String[] masStr = arrayContent[i].split(context.getString(R.string.symbolSeparationValues));
                    noteList.add(new Note(masStr[0], masStr[1], masStr[2], masStr[3]));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // закрываем поток
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Collections.sort(noteList, new ComparatorNotes());
        return noteList;
    }

    @Override
    public void saveNote(Note note) {
        BufferedWriter bw = null;
        try {
            if (connection()) {
                // отрываем поток для записи
                bw = new BufferedWriter(new OutputStreamWriter(
                        context.openFileOutput(fileName, MODE_APPEND)));
            } else {
                bw = new BufferedWriter(new OutputStreamWriter(
                        context.openFileOutput(fileName, MODE_PRIVATE)));
            }

            // пишем данные
            bw.write((note.getHeadline() == null ? "" : note.getHeadline()) + context.getString(R.string.symbolSeparationValues) +
                         (note.getTextNote() == null ? "" : note.getTextNote()) + context.getString(R.string.symbolSeparationValues) +
                         (note.getDateDeadline() == null ? "" : note.getDateDeadline()) + context.getString(R.string.symbolSeparationValues) +
                          note.getDateUpdateNote() + "\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // закрываем поток
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteById(String id) {
        List<Note> listNotes = getNotes();
        listNotes.remove(Integer.parseInt(id));

        BufferedWriter bw = null;

        try {
            bw = new BufferedWriter(new OutputStreamWriter(
                    context.openFileOutput(fileName, MODE_PRIVATE)));
            bw.write("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (Note note : listNotes) {
            saveNote(note);
        }
    }
}

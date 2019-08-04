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
        // Есть ли смысл это все закидывать в string. Если это все тестовое? или создать отдельный файл?
        String dateNow = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());
        saveNote(new Note("Заголовок", "4 Тело заметки", "15.09.2019", dateNow));
        saveNote(new Note(null, "10 Только тело заметки 1", null, "01.07.2018"));
        saveNote(new Note(null, "8-9 Только тело заметки 2", null, "17.07.2019"));
        saveNote(new Note("Заметка с длинным текстом", "8-9 Заметки сортируются по дате дедлайна: чем ближе срок истечения, тем выше заметка в списке (просроченные заметки оказываются в самом верху)." +
                "Если дедлайны совпали или заметка не имеет дедлайна, тогда сортировка происходит по дате последнего изменения (новые или отредактированные оказываются выше)." +
                "Любая заметка с дедлайном всегда выше заметки без дедлайна.", null, "01.07.2019"));
        saveNote(new Note(null, "6 Заметка без заголовка, но с текстом и дедлайном 2", "16.09.2019", "16.06.2019"));
        saveNote(new Note(null, "5 Заметка без заголовка, но с текстом и дедлайном 1", "16.09.2019", dateNow));
        saveNote(new Note("Заметка с длинным заголовком и небольшим текстом", "3 Заметка с длинным заголовка", "01.04.2019", dateNow));
        saveNote(new Note("Заметка с истекшим сроком", "2 Заметка с заголовком и истекшим сроком", "04.05.2018", "03.05.2018"));
        saveNote(new Note(null, "7 Только тело заметки 3", null, dateNow));
        saveNote(new Note("Заметка с истекшим сроком 2", "1 Заметка с заголовком и истекшим сроком", "04.05.2018", "04.05.2018"));
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

package ru.netologia.sharinm_diplom_notes;

import android.content.Context;
import android.util.Log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;

public class FileNoteRepository implements NoteRepository{

    private final String LOG_TAG_JSON = "JSONNoteRepository";
    private Context context;
    private String fileName;

    FileNoteRepository(Context context, String fileName){
        this.context = context;
        this.fileName = fileName;
    }

    @Override
    public boolean connection() {
        File file = context.getFileStreamPath(fileName);
        if(file == null || !file.exists()) {
            return false;
        }
        return true;
    }

    @Override
    public void createDefaultNotes(){
        String dateNow = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());
        saveNote(new Note(null, "Только тело заметки", null, dateNow));
        saveNote(new Note("Заметка с длинным текстом","Заметки сортируются по дате дедлайна: чем ближе срок истечения, тем выше заметка в списке (просроченные заметки оказываются в самом верху)." +
                "Если дедлайны совпали или заметка не имеет дедлайна, тогда сортировка происходит по дате последнего изменения (новые или отредактированные оказываются выше)." +
                "Любая заметка с дедлайном всегда выше заметки без дедлайна.", null, dateNow));
        Date dt = new Date();
        String date = "15.09.2019";
        //TODO: поставить даты нормальные
        saveNote(new Note(null, "Заметка без заголовка, но с текстом и дедлайном", date, dateNow));
        date = "16.09.2019";
        //TODO: поставить даты нормальные
        saveNote(new Note("Заголовок","Тело заметки", date, dateNow));
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
            String str = "";

            // читаем содержимое
            while ((str = br.readLine()) != null) {

                String[] arrayContent = str.split("\n");

                for (int i = 0; i < arrayContent.length; i++) {
                    String[] masStr = arrayContent[i].split(";");
                        noteList.add(new Note(masStr[0], masStr[1], masStr[2], masStr[3]));
                }
                Log.d(LOG_TAG_JSON, str);
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
        return noteList;
    }

    @Override
    public void saveNote(Note note) {
        BufferedWriter bw = null;
        try {
            if(connection()){
            // отрываем поток для записи
            bw = new BufferedWriter(new OutputStreamWriter(
                    context.openFileOutput(fileName, MODE_APPEND)));
            } else {
                bw = new BufferedWriter(new OutputStreamWriter(
                        context.openFileOutput(fileName, MODE_PRIVATE)));
            }

            // пишем данные
            bw.write((note.getHeadline() == null ? "" : note.getHeadline()) + ";" + (note.getTextNote() == null ? "" : note.getTextNote())
                            + ";" + (note.getDateDeadline() == null ? "" : note.getDateDeadline())+ ";" + note.getDateUpdateNote() + "\n");

            Log.d(LOG_TAG_JSON, "Файл записан");
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

        for (Note note : listNotes ) {

            saveNote(note);
        }

    }

/*
    static boolean exportToJSON(Context context, List<Note> dataList) {

        Gson gson = new Gson();
        DataItems dataItems = new DataItems();
        dataItems.setNotes(dataList);
        String jsonString = gson.toJson(dataItems);

        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fileOutputStream.write(jsonString.getBytes());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    static List<Note> importFromJSON(Context context) {

        InputStreamReader streamReader = null;
        FileInputStream fileInputStream = null;
        try{
            fileInputStream = context.openFileInput(FILE_NAME);
            streamReader = new InputStreamReader(fileInputStream);
            Gson gson = new Gson();
            DataItems dataItems = gson.fromJson(streamReader, DataItems.class);
            return  dataItems.getNotes();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        finally {
            if (streamReader != null) {
                try {
                    streamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private static class DataItems {
        private List<Note> notes;

        List<Note> getNotes() {
            return notes;
        }
        void setNotes(List<Note> notes) {
            this.notes = notes;
        }
    }*/
}
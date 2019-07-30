package ru.netologia.sharinm_diplom_notes;

import android.app.Application;

public class App extends Application {

    private static NoteRepository noteRepository;
    private static Keystore hashedKeystore;
    public static final String FILE_NAME = "data.json";

    @Override
    public void onCreate() {
        super.onCreate();

        /* Конкретная реализация выбирается только здесь.
           Изменением одной строчки здесь,
           мы заменяем реализацию во всем приложении!
        */

        noteRepository = new FileNoteRepository(this, FILE_NAME);
        hashedKeystore = new HashedKeystore(this);
    }

    // Возвращаем интерфейс, а не конкретную реализацию!
    public static NoteRepository getNoteRepository() {
        return noteRepository;
    }

    // Возвращаем интерфейс, а не конкретную реализацию!
    public static Keystore getKeystore() {
        return hashedKeystore;
    }
}



package ru.netologia.sharinm_diplom_notes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class NoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null){
            return;
        }

        if(bundle.getInt(MainActivity.POSITION_LISTVIEW) == 0){
         // TODO:   новая заявка
        } else {
           //TODO:  открытие старой
        }
    }
}

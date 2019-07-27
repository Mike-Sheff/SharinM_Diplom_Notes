package ru.netologia.sharinm_diplom_notes;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class NoteActivity extends AppCompatActivity {

    private EditText dataDeadline;
    public final String LOG_TAG_NOTE = "Note";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        initViews();
    }

    public void initViews() {

        {
            Bundle bundle = getIntent().getExtras();
            if (bundle == null) {
                return;
            }

            if (bundle.getInt(MainActivity.POSITION_LISTVIEW) == 0) {
                // TODO:   новая заявка
            } else {
                //TODO:  открытие старой
            }
        }

        Toolbar toolbar = findViewById(R.id.toolbarNote);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        dataDeadline = findViewById(R.id.editTextDataDeadline);

        final CheckBox checkBoxDeadline = findViewById(R.id.chechboxDeadline);
        checkBoxDeadline.setOnCheckedChangeListener(checkedChangeListener);

        Button btnCalendar = findViewById(R.id.buttonCalendar);
        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxDeadline.isChecked()) {

                    //TODO: отображение календаря и выбор даты
                    Toast.makeText(NoteActivity.this, "fdd", Toast.LENGTH_SHORT).show();
                    Log.d(MainActivity.LOG_TAG + LOG_TAG_NOTE, "--- Обработка кнопки с календарем ---");
                }
            }
        });

        dataDeadline.addTextChangedListener(new DateInputMask(dataDeadline));
    }

    CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {
                dataDeadline.setEnabled(true);
                dataDeadline.requestFocus();
                Log.d(MainActivity.LOG_TAG + LOG_TAG_NOTE, "--- Обработка установка галки срока заметки ---");

            } else {
                dataDeadline.setEnabled(false);
                dataDeadline.setText("");

                Log.d(MainActivity.LOG_TAG + LOG_TAG_NOTE, "--- Обработка снятия галки срока заметки ---");
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_save:

                //TODO: сохранение заметки
                Log.d(MainActivity.LOG_TAG + LOG_TAG_NOTE, "--- Обработка кнопки сохранения заметки ---");
                return true;
            case android.R.id.home:
                onBackPressed();
                Log.d(MainActivity.LOG_TAG + LOG_TAG_NOTE, "--- Обработка кнопки возврващения в main activity ---");

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
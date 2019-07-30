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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NoteActivity extends AppCompatActivity {

    private EditText dataDeadline;
    public final String LOG_TAG_NOTE = "Note";
    private Bundle bundle;
    private EditText textNote, headline;
    private NoteRepository fileNoteRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        initViews();
    }

    public void initViews() {

        fileNoteRepository = new FileNoteRepository(this, MainActivity.FILE_NAME);

        headline = findViewById(R.id.editTextHeadlineNote);
        textNote = findViewById(R.id.editTextTextNote);
        dataDeadline = findViewById(R.id.editTextDataDeadline);

        {
            bundle = getIntent().getExtras();
            if (bundle == null) {
                return;
            }

            if (bundle.getInt(MainActivity.POSITION_LISTVIEW) != -1) {

                Note note = fileNoteRepository.getNoteById(Integer.toString(bundle.getInt(MainActivity.POSITION_LISTVIEW)));

                if(note.getHeadline().length() != 0) {
                    headline.setText(note.getHeadline());
                }
                if(note.getTextNote().length() != 0) {
                    textNote.setText(note.getTextNote());
                }

                CheckBox checkBox = findViewById(R.id.chechboxDeadline);

                if (note.getDateDeadline().length() != 0 ) {
                    checkBox.setChecked(true);
                    dataDeadline.setEnabled(true);
                    dataDeadline.setText(note.getDateDeadline());
                } else {
                    checkBox.setChecked(false);
                }
            }
        }

        Toolbar toolbar = findViewById(R.id.toolbarNote);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

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

                if (bundle.getInt(MainActivity.POSITION_LISTVIEW) == -1) {
                    if (textNote.getText().length() == 0 ){
                        Toast.makeText(this, "Для сохранения: тело заметки не должно быть пустым!", Toast.LENGTH_LONG).show();
                    } else {
                        fileNoteRepository.saveNote(new Note((headline.getText().length() == 0 ? null: headline.getText().toString())
                                                              , textNote.getText().toString()
                                                              ,(dataDeadline.getText().length() == 0 ? null : dataDeadline.getText().toString())
                                                              , new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date())));

                        Toast.makeText(this, "Заметка сохранена!", Toast.LENGTH_LONG).show();

                    }
                } else {
                    fileNoteRepository.deleteById(String.valueOf(bundle.getInt(MainActivity.POSITION_LISTVIEW)));
                    fileNoteRepository.saveNote(new Note((headline.getText().length() == 0 ? null: headline.getText().toString())
                                                            , textNote.getText().toString()
                                                            ,(dataDeadline.getText().length() == 0 ? null : dataDeadline.getText().toString())
                                                            , new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date())));
                    
                    Toast.makeText(this, "Заметка обнавлена!", Toast.LENGTH_LONG).show();

                    //TODO: сохранение старой заметки
                }

                finish();

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

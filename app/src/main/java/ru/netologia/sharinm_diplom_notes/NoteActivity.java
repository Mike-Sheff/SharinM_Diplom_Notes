package ru.netologia.sharinm_diplom_notes;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NoteActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText dataDeadline;
    private Bundle bundle;
    private EditText textNote, headline;
    private NoteRepository fileNoteRepository;
    private final String TAG_DATE_PICKER = "date picker";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        initViews();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        ++month;
        dataDeadline.setText((dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "." + (month < 10 ? "0" + month : month) + "." + year);
    }

    public void initViews() {

        fileNoteRepository = App.getNoteRepository();
        fileNoteRepository = new FileNoteRepository(this, MainActivity.FILE_NAME);

        fileNoteRepository = App.getNoteRepository();

        headline = findViewById(R.id.editTextHeadlineNote);
        textNote = findViewById(R.id.editTextTextNote);
        dataDeadline = findViewById(R.id.editTextDataDeadline);
        final CheckBox checkBox = findViewById(R.id.chechboxDeadline);

        {
            bundle = getIntent().getExtras();
            if (bundle == null) {
                return;
            }

            if (bundle.getInt(MainActivity.POSITION_LISTVIEW) != -1) {

                Note note = fileNoteRepository.getNoteById(Integer.toString(bundle.getInt(MainActivity.POSITION_LISTVIEW)));

                if (note.getHeadline().length() != 0) {
                    headline.setText(note.getHeadline());
                }
                if (note.getTextNote().length() != 0) {
                    textNote.setText(note.getTextNote());
                }

                if (note.getDateDeadline().length() != 0) {
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

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        checkBox.setOnCheckedChangeListener(checkedChangeListener);

        Button btnCalendar = findViewById(R.id.buttonCalendar);
        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {

                    DialogFragment datePicker = new DatePickerFragment();
                    datePicker.show(getSupportFragmentManager(), TAG_DATE_PICKER);
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
            } else {
                dataDeadline.setEnabled(false);
                dataDeadline.setText("");
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
                    if (textNote.getText().length() == 0) {
                        Toast.makeText(this, getString(R.string.textErrorMessageSaveNote), Toast.LENGTH_LONG).show();
                    } else {
                        fileNoteRepository.saveNote(new Note((headline.getText().length() == 0 ? null : headline.getText().toString())
                                , textNote.getText().toString()
                                , (dataDeadline.getText().length() == 0 ? null : dataDeadline.getText().toString())
                                , new SimpleDateFormat(getString(R.string.formatDate), Locale.getDefault()).format(new Date())));

                        Toast.makeText(this, getString(R.string.textMessageSaveNote), Toast.LENGTH_LONG).show();

                    }
                } else {
                    fileNoteRepository.deleteById(String.valueOf(bundle.getInt(MainActivity.POSITION_LISTVIEW)));
                    fileNoteRepository.saveNote(new Note((headline.getText().length() == 0 ? null : headline.getText().toString())
                            , textNote.getText().toString()
                            , (dataDeadline.getText().length() == 0 ? null : dataDeadline.getText().toString())
                            , new SimpleDateFormat(getString(R.string.formatDate), Locale.getDefault()).format(new Date())));

                    Toast.makeText(this, getString(R.string.textMessageUpdateNote), Toast.LENGTH_LONG).show();
                }

                finish();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

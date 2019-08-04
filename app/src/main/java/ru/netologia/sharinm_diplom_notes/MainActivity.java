package ru.netologia.sharinm_diplom_notes;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public final String SHARED_PREFERENCES_APP_NAME = "mySharePref";
    public static final String SHARED_PREFERENCES_APP_PASSWORD = "AppPassword";
    public static final String POSITION_LISTVIEW = "Position";
    public static SharedPreferences mySharedPreferences;
    private Intent intent;
    private NoteAdapter adapter;
    private static long back_pressed;
    public final String LOG_TAG_MAIN = "Main";
    private NoteRepository fileNoteRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mySharedPreferences = getSharedPreferences(SHARED_PREFERENCES_APP_NAME, MODE_PRIVATE);

        if (App.getKeystore().hasPassword()) {
            // Вывод окна с вводом пароля
            intent = new Intent(MainActivity.this, LoginActivity.class);

            if (intent != null) {
                startActivity(intent);
            }
        }

        initViews();
    }

    public void displayNotes() {

        List<Note> notes = fileNoteRepository.getNotes();

        if (notes != null) {

            adapter.refresh(notes);
        }
    }

    public void initViews() {
        fileNoteRepository = App.getNoteRepository();

        final ListView listView = findViewById(R.id.listView);
        adapter = new NoteAdapter(this, null);
        listView.setAdapter(adapter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNoteActivity(-1);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openNoteActivity(position);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                new AlertDialog.Builder(MainActivity.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle(getString(R.string.textTitleDialogDeleteNote))
                        .setMessage(getString(R.string.textQuestionDialogDelete))
                        .setPositiveButton(getString(R.string.textDialogPositiveButton), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                adapter.removeNote(position);

                                fileNoteRepository.deleteById(String.valueOf(position));

                                displayNotes();
                            }
                        }).setNegativeButton(getString(R.string.textDialogNegativeButton), null).show();
                return true;
            }
        });

        if (!fileNoteRepository.connection()) {
            fileNoteRepository.createDefaultNotes();
            Toast.makeText(this, getString(R.string.textMessageCreateDefualtNotes), Toast.LENGTH_SHORT).show();
        }

        displayNotes();
    }

    public void openNoteActivity(int position) {

        intent = new Intent(MainActivity.this, NoteActivity.class);

        intent.putExtra(POSITION_LISTVIEW, position);

        if (intent != null) {
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        displayNotes();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            intent = new Intent(MainActivity.this, SettingsActivity.class);

            if (intent != null) {
                startActivity(intent);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            finish();
        } else {
            Toast.makeText(getBaseContext(), getString(R.string.textMessageExit), Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }
}

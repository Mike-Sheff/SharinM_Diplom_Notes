package ru.netologia.sharinm_diplom_notes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = "myLogs:";
    public final String SHARED_PREFERENCES_APP_NAME = "mySharePref";
    public static final String SHARED_PREFERENCES_APP_PASSWORD = "AppPassword";

    public static SharedPreferences mySharedPreferences;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViews();

        if ((new HashedKeystore()).hasPassword()){
            // Вывод окна с вводом пароля
            intent = new Intent(MainActivity.this, LoginActivity.class);
            if(intent != null){
                startActivity(intent);
            }
        }

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            //    intent = new Intent(MainActivity.this, NoteActivity.class);

                if (intent != null) {
                    startActivity(intent);
                }
            }
        });
    }

    public void initViews(){

        mySharedPreferences = getSharedPreferences(SHARED_PREFERENCES_APP_NAME, MODE_PRIVATE);

        //if(!mySharedPreferences.contains(SHARED_PREFERENCES_APP_PASSWORD)) {
       // if((new HashedKeystore()).hasPassword()){
         //   HashedKeystore hashedKeystore = new HashedKeystore();
        //    hashedKeystore.saveDefaultPassword();
        //}
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
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);

            if (intent != null) {
                startActivity(intent);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

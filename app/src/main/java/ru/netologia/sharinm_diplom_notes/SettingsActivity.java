package ru.netologia.sharinm_diplom_notes;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    private boolean lookPassword = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initViews();
    }

    public void initViews() {

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        final EditText editNewPassword = findViewById(R.id.editTextPassword);

        Button btnSavePassword = findViewById(R.id.buttonSavePassword);
        btnSavePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editNewPassword.length() == 4) {
                    App.getKeystore().saveNewPassword(editNewPassword.getText().toString());

                    editNewPassword.setText("");
                    Toast.makeText(SettingsActivity.this, getString(R.string.textMessageSavePassword), Toast.LENGTH_SHORT).show();

                    finish();
                } else {
                    Toast.makeText(SettingsActivity.this, getString(R.string.textErrorLengthPassword), Toast.LENGTH_SHORT).show();
                }
            }
        });

        final ImageButton btnLookPassword = findViewById(R.id.buttonLookPassword);
        btnLookPassword.setBackgroundResource(R.mipmap.ic_eye_not_look);
        btnLookPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (lookPassword) {
                    btnLookPassword.setBackgroundResource(R.mipmap.ic_eye_not_look);
                    editNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    btnLookPassword.setBackgroundResource(android.R.drawable.ic_menu_view);
                    editNewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }

                editNewPassword.setSelection(editNewPassword.getText().length());
                lookPassword = !lookPassword;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
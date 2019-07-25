package ru.netologia.sharinm_diplom_notes;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    private static final String LOG_TAG_SETTINGS = "Settings";
    private boolean lookPassword = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final EditText editNewPassword = findViewById(R.id.editTextPassword);

        Button btnSavePassword = findViewById(R.id.buttonSavePassword);
        btnSavePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editNewPassword.length() == 4) {
                    HashedKeystore hashedKeystore = new HashedKeystore();
                    hashedKeystore.saveNewPassword(editNewPassword.getText().toString());

                    Toast.makeText(SettingsActivity.this, getString(R.string.textMessageSavePassword), Toast.LENGTH_SHORT).show();

                    Log.d(MainActivity.LOG_TAG + LOG_TAG_SETTINGS, "--- Нажата кнопка сохранения пароля ---");
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
                    Log.d(MainActivity.LOG_TAG + LOG_TAG_SETTINGS, "--- Нажата кнопка для скрытия пароля ---");
                } else {
                    btnLookPassword.setBackgroundResource(R.mipmap.ic_eye_look);
                    editNewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    Log.d(MainActivity.LOG_TAG + LOG_TAG_SETTINGS, "--- Нажата кнопка для отображение пароля ---");
                }

                editNewPassword.setSelection(editNewPassword.getText().length());
                lookPassword = !lookPassword;
            }
        });
    }
}

package ru.netologia.sharinm_diplom_notes;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {

    private static final String LOG_TAG_LOGIN = "Login";

    private String enteredUserPassword = "";
    private String Preference_Password;

    private int[] images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
    }

    private void initViews() {

        NumberButtonClickListener numberListener = new NumberButtonClickListener();

        int[] buttonIdNumber = new int[]{R.id.buttonNumberOne, R.id.buttonNumberTwo
                , R.id.buttonNumberThree, R.id.buttonNumberFour
                , R.id.buttonNumberFive, R.id.buttonNumberSix
                , R.id.buttonNumberSeven, R.id.buttonNumberEight
                , R.id.buttonNumberNine, R.id.buttonNumberZero
                , R.id.buttonNumberBackspace};

        for (int buttonId : buttonIdNumber) {
            findViewById(buttonId).setOnClickListener(numberListener);
        }

        images = new int[]{R.id.profile_image1, R.id.profile_image2
                , R.id.profile_image3, R.id.profile_image4};
    }

    class NumberButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int idButtonClicked;

            // проверка что пароль не привышает 4 символов
            // при длине пароля в 4 символа можно нажимать только кнопку удаления символа
            // при нажатии любой другой кнопки выдавать сообщение об ошибке в пароле

            /* вариант 1
            idButtonClicked = 0;

            switch (enteredUserPassword.length()) {
                case 0:
                case 1:
                case 2:
                case 3:
                    idButtonClicked = v.getId();
                    break;
                case 4:
                    if (v.getId() == R.id.buttonNumberBackspace) {
                        idButtonClicked = v.getId();
                    }
                    break;
                default:
                    idButtonClicked = 0;
                    break;
            }
            */

            // вариант 2
            if (enteredUserPassword.length() < 4) {
                idButtonClicked = v.getId();
            } else if (v.getId() == R.id.buttonNumberBackspace) {
                idButtonClicked = v.getId();
            } else {
                idButtonClicked = 0; // когда пароль превышает 4 символов
            }

            Log.d(MainActivity.LOG_TAG + LOG_TAG_LOGIN, "--- Обработка кнопки " + idButtonClicked + " ---");

            switch (idButtonClicked) {
                case R.id.buttonNumberZero:
                    enteredUserPassword += getString(R.string.numberZero);
                    break;
                case R.id.buttonNumberOne:
                    enteredUserPassword += getString(R.string.numberOne);
                    break;
                case R.id.buttonNumberTwo:
                    enteredUserPassword += getString(R.string.numberTwo);
                    break;
                case R.id.buttonNumberThree:
                    enteredUserPassword += getString(R.string.numberThree);
                    break;
                case R.id.buttonNumberFour:
                    enteredUserPassword += getString(R.string.numberFour);
                    break;
                case R.id.buttonNumberFive:
                    enteredUserPassword += getString(R.string.numberFive);
                    break;
                case R.id.buttonNumberSix:
                    enteredUserPassword += getString(R.string.numberSix);
                    break;
                case R.id.buttonNumberSeven:
                    enteredUserPassword += getString(R.string.numberSeven);
                    break;
                case R.id.buttonNumberEight:
                    enteredUserPassword += getString(R.string.numberEight);
                    break;
                case R.id.buttonNumberNine:
                    enteredUserPassword += getString(R.string.numberNine);
                    break;
                case R.id.buttonNumberBackspace:
                    if (enteredUserPassword.length() != 0) {
                        //вариант 1
                        enteredUserPassword = enteredUserPassword.substring(0, enteredUserPassword.length() - 1);
                        // вариант 2
                        //enteredUserPassword = enteredUserPassword.replaceFirst(".$","");
                        // вариант 3
                        //enteredUserPassword = StringUtils.substring(enteredUserPassword, 0, -1);
                    }
                    break;
            }

            for (int i = 0; i < images.length; i++) {

                Log.d(MainActivity.LOG_TAG + LOG_TAG_LOGIN, "--- Закрашивание image при введенных символах ---");

                if (i < enteredUserPassword.length()) {
                    //TODO:  подправить цвета
                    findViewById(images[i]).setBackgroundColor(Color.rgb(255, 193, 7));
                } else {
                    findViewById(images[i]).setBackgroundColor(Color.rgb(80, 80, 80));
                }
            }

            if (enteredUserPassword.length() == 4) {

                Log.d(MainActivity.LOG_TAG + LOG_TAG_LOGIN, "--- Проверка пароля ---");

                HashedKeystore hashedKeystore = new HashedKeystore();

                if (hashedKeystore.checkPassword(enteredUserPassword)) {
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, R.string.textErrorPassword, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}

package com.example.peanuts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    /** Called when the user clicks the login button */
    public void launchMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
//        EditText editText = (EditText) findViewById(R.id.editText);
//        String name = editText.getText().toString();
        // permanently store name for future app launches and for nav header
        startActivity(intent);
    }

    public void lauchNewAccount(View view) {
        Intent intent = new Intent(this, NewAccount.class);
        startActivity(intent);
    }
}

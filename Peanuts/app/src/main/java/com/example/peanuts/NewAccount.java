package com.example.peanuts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class NewAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);
    }

    public void launchRestrictions(View view) {
        Intent intent = new Intent(this, EditRestrictions.class);
        startActivity(intent);
    }

    public void lauchLogin(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}
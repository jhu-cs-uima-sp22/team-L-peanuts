package com.example.peanuts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.peanuts.MainActivity;
import com.example.peanuts.R;

public class Settings extends AppCompatActivity {

    private SharedPreferences preferences;
    private MainActivity myact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //myact = (MainActivity) getActivity();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Context context = getApplicationContext();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        TextView name_text = (TextView) findViewById(R.id.name_text);
        name_text.setText(preferences.getString("user_name", ""));
        TextView email_text = (TextView) findViewById(R.id.email_text);
        email_text.setText(preferences.getString("user_email", ""));
        TextView password_text = (TextView) findViewById(R.id.password_text);
        password_text.setText(preferences.getString("user_password", ""));
    }

    public void save(View view) {
        TextView textViewName = (TextView) findViewById(R.id.name_text);
        String name = textViewName.getText().toString();
        TextView textViewPassword = (TextView) findViewById(R.id.password_text);
        String password = textViewPassword.getText().toString();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user_name", name);
        editor.putString("user_password", password);
        editor.apply();

        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
package com.example.peanuts.ui.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
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

    private SharedPreferences myPrefs;
    private MainActivity myact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //myact = (MainActivity) getActivity();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Context context = myact.getApplicationContext();
        myPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void back(View view) {
        TextView textView = (TextView) findViewById(R.id.name_text);
        String name = textView.getText().toString();
        SharedPreferences.Editor peditor = myPrefs.edit();
        peditor.putString("NAME", name);
        peditor.apply();

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
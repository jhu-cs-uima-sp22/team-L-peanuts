package com.example.peanuts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

public class FoodDetail extends AppCompatActivity {

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        Context context = getApplicationContext();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        TextView name = findViewById(R.id.food_name);
        ImageView image = findViewById(R.id.food_image);
        TextView allergens = findViewById(R.id.food_allergens);

        Intent intent = getIntent();
        name.setText(intent.getStringExtra("name"));
        image.setImageURI(Uri.parse(intent.getStringExtra("image")));
        boolean[] restrictions = intent.getBooleanArrayExtra("restrictions");
        String restrictionsText = "";
        for (int i = 0; i < restrictions.length; i++) {
            if (restrictions[i]) {
                restrictionsText = restrictionsText + "-" + preferences.getString("" + i, "") + "\n";
            }
        }
        allergens.setText(restrictionsText);
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
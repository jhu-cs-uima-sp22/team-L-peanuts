package com.example.peanuts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

public class FoodDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        TextView name = findViewById(R.id.food_name);
        ImageView image = findViewById(R.id.food_image);
        TextView allergens = findViewById(R.id.food_allergens);

        Intent intent = getIntent();
        name.setText(intent.getStringExtra("name"));
        image.setImageDrawable(getResources().getDrawable(intent.getIntExtra("image", R.drawable.spaghetti)));
        allergens.setText(intent.getStringExtra("allergens"));
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
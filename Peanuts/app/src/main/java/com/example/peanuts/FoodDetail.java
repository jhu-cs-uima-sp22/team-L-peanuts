package com.example.peanuts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FoodDetail extends AppCompatActivity {

    private SharedPreferences preferences;
    private String user;
    private DatabaseReference myRefForFoods;
    private FirebaseDatabase databaseForFoods;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        Context context = getApplicationContext();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        databaseForFoods = FirebaseDatabase.getInstance("https://peanuts-e397e-default-rtdb.firebaseio.com/");
        myRefForFoods = databaseForFoods.getReference("Users");

        TextView name = findViewById(R.id.food_name);
        ImageView image = findViewById(R.id.food_image);
        TextView allergens = findViewById(R.id.food_allergens);

        Intent intent = getIntent();
        name.setText(intent.getStringExtra("name"));

        int position = intent.getIntExtra("position", -1);

        user = intent.getStringExtra("user");


        FoodItem item = new FoodItem("", ""); //create empty item


        if (position == -1) {
            String path = intent.getStringExtra("image");
            item.setImage(path, image); //set image
        } else {

            myRefForFoods.child(user).child("" + position).addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String path = (String) dataSnapshot.child("imageUri").getValue();
                    item.setImage(path, image);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        boolean[] restrictions = intent.getBooleanArrayExtra("restrictions");
        ArrayList<String> allergensList = intent.getStringArrayListExtra("allergens");
        String restrictionsText = "";
        if (restrictions != null) {
            for (int i = 0; i < restrictions.length; i++) {
                if (restrictions[i]) {
                    restrictionsText = restrictionsText + "-" + preferences.getString("" + i, "") + "\n";
                }
            }
        } else if (allergensList != null) {
            for (int i = 0; i < allergensList.size(); i++) {
                restrictionsText = restrictionsText + "-" + allergensList.get(i) + "\n";
            }
        } else {
            restrictionsText = "none";
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
package com.example.peanuts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FoodDetail extends AppCompatActivity {

    private SharedPreferences preferences;
    private StorageReference storageReference;
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
        String strName = intent.getStringExtra("name");
        Log.d("debug", "Name in detail: " + strName);
        //image.setImageURI(Uri.parse(intent.getStringExtra("image")));

        int position = intent.getIntExtra("position", -1);
        Log.d("debug", "Position in detail: " + position);

        user = intent.getStringExtra("user");


        FoodItem item = new FoodItem("", ""); //create empty item


        if (position == -1) {
            String path = intent.getStringExtra("image");
            item.setImage(path, image); //set image
            Log.d("debug", "Path in detail: " + path);
        } else {

            myRefForFoods.child(user).child("" + position).addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String path = (String) dataSnapshot.child("imageUri").getValue();
                    item.setImage(path, image);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("retrieve_fail", databaseError.toString());
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
            //allergens.setText(restrictionsText);
        } else if (allergensList != null) {
            for (int i = 0; i < allergensList.size(); i++) {
                restrictionsText = restrictionsText + "-" + allergensList.get(i) + "\n";
            }
        } else {
            restrictionsText = "none";
        }

        allergens.setText(restrictionsText);
        Log.d("debug", "Restrictions in detail: " + restrictionsText);
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
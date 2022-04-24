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
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FoodDetail extends AppCompatActivity {

    private SharedPreferences preferences;
    private StorageReference storageReference;
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
        //image.setImageURI(Uri.parse(intent.getStringExtra("image")));
        String path = intent.getStringExtra("image");
        FoodItem item = new FoodItem("", ""); //create empty item
        item.setImage(path, image); //set image

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
package com.example.peanuts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.peanuts.MainActivity;
import com.example.peanuts.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditFoods extends AppCompatActivity {

    private ListView myList;
    protected ArrayList<FoodItem> foodItems;
    protected FoodItemAdapter adapter;
    private DatabaseReference myRefForFoods;
    private FirebaseDatabase databaseForFoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_foods);

        databaseForFoods = FirebaseDatabase.getInstance("https://peanuts-e397e-default-rtdb.firebaseio.com/");
        myRefForFoods = databaseForFoods.getReference("Users");

        Context context = getApplicationContext();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        String email = preferences.getString("user_email", "");
        myList = (ListView) this.findViewById(R.id.myFoodList);
        foodItems = new ArrayList<>();
        myRefForFoods.child(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                foodItems = (ArrayList<FoodItem>) dataSnapshot.getValue();
                if (foodItems == null) {
                    foodItems = new ArrayList<>();
                }
                Context context = getApplicationContext();
                adapter = new FoodItemAdapter(context, R.layout.food_layout, foodItems, email);
                myList.setAdapter(adapter);
                registerForContextMenu(myList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                foodItems = new ArrayList<>();
            }
        });
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
package com.example.peanuts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class GroupActivity extends AppCompatActivity {
    private String id;
    private SharedPreferences preferences;
    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://peanuts-e9a7c-default-rtdb.firebaseio.com/");
    private DatabaseReference myRef = database.getReference();
    private List<String> foodIds;
    private List<FoodItem> foods;
    private List<String> restrictions;
    private List<NewAccount.User> members;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        Context context = getApplicationContext();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        ConstraintLayout placeholder = (ConstraintLayout) findViewById(R.id.PlaceHolder);
        HorizontalScrollView restrictionsView = (HorizontalScrollView) findViewById(R.id.RestrictionsInGroups);
        ConstraintLayout response = (ConstraintLayout) findViewById(R.id.MealPlanResponse);
        foodIds = new ArrayList<>();
        foods = new ArrayList<>();
        restrictions = new ArrayList<>();
        members = new ArrayList<>();
        myRef.child("groups").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //GroupItem group = (GroupItem) dataSnapshot.getValue();
                String name = (String) dataSnapshot.child("groupName").getValue();
                setTitle(name);
                boolean isHost = preferences.getString("user_email", "").equals((String) dataSnapshot.child("host").getValue());
                //**ISSUE HERE  TO_DO**
                //foodIds = (ArrayList<String>) dataSnapshot.child("foods").getValue();
                foods = new ArrayList<>();

                //**FOR TESTING**
                boolean[] booleans = new boolean[12];
                booleans[5] = true;
                foods.add(new FoodItem("Spaghetti", booleans, getDrawable(R.drawable.spaghetti)));
                foods.add(new FoodItem("Spaghetti", booleans, getDrawable(R.drawable.spaghetti)));
                foods.add(new FoodItem("Spaghetti", booleans, getDrawable(R.drawable.spaghetti)));
                foods.add(new FoodItem("Spaghetti", booleans, getDrawable(R.drawable.spaghetti)));
                foods.add(new FoodItem("Spaghetti", booleans, getDrawable(R.drawable.spaghetti)));
                //**FOR TESTING**

                members = (ArrayList<NewAccount.User>) dataSnapshot.child("members").getValue();
                restrictions = (ArrayList<String>) dataSnapshot.child("restrictions").getValue();
                if (isHost) {
                    placeholder.setVisibility(View.INVISIBLE);
                    restrictionsView.setVisibility(View.VISIBLE);
                } else {
                    if (foods.isEmpty()) { //check if meal plan is empty
                        //if meal plan is empty
                        placeholder.setVisibility(View.VISIBLE);
                        response.setVisibility(View.INVISIBLE);
                        restrictionsView.setVisibility(View.INVISIBLE);
                    } else {
                        //if meal plan exists
                        placeholder.setVisibility(View.INVISIBLE);
                        response.setVisibility(View.VISIBLE);
                    }
                }
                ConstraintLayout cardView;
                if (!restrictions.contains("Peanuts")) {
                    cardView = (ConstraintLayout) findViewById(R.id.peanuts);
                    cardView.setMaxWidth(0);
                }
                if (!restrictions.contains("Dairy")) {
                    cardView = (ConstraintLayout) findViewById(R.id.dairy);
                    cardView.setMaxWidth(0);
                }
                if (!restrictions.contains("Seafood")) {
                    cardView = (ConstraintLayout) findViewById(R.id.seafood);
                    cardView.setMaxWidth(0);
                }
                if (!restrictions.contains("Soy")) {
                    cardView = (ConstraintLayout) findViewById(R.id.soy);
                    cardView.setMaxWidth(0);
                }
                if (!restrictions.contains("Strawberries")) {
                    cardView = (ConstraintLayout) findViewById(R.id.strawberries);
                    cardView.setMaxWidth(0);
                }
                if (!restrictions.contains("Shellfish")) {
                    cardView = (ConstraintLayout) findViewById(R.id.shellfish);
                    cardView.setMaxWidth(0);
                }
                if (!restrictions.contains("Eggs")) {
                    cardView = (ConstraintLayout) findViewById(R.id.eggs);
                    cardView.setMaxWidth(0);
                }
                if (!restrictions.contains("Tree Nuts")) {
                    cardView = (ConstraintLayout) findViewById(R.id.treenuts);
                    cardView.setMaxWidth(0);
                }
                if (!restrictions.contains("Wheat")) {
                    cardView = (ConstraintLayout) findViewById(R.id.wheat);
                    cardView.setMaxWidth(0);
                }
                if (!restrictions.contains("Gluten")) {
                    cardView = (ConstraintLayout) findViewById(R.id.gluten);
                    cardView.setMaxWidth(0);
                }
                if (!restrictions.contains("Avocado")) {
                    cardView = (ConstraintLayout) findViewById(R.id.avocado);
                    cardView.setMaxWidth(0);
                }
                if (!restrictions.contains("Sesame")) {
                    cardView = (ConstraintLayout) findViewById(R.id.sesame);
                    cardView.setMaxWidth(0);
                }
                GroupMemberAdapter memberAdapter = new GroupMemberAdapter(context, R.layout.group_users_layout, members);
                ListView membersList = (ListView) findViewById(R.id.ResponseList);
                membersList.setAdapter(memberAdapter);
                registerForContextMenu(membersList);
                memberAdapter.notifyDataSetChanged();

                //populate the meal plan with current foods
                GroupMealPlanAdapter mealPlanAdapter = new GroupMealPlanAdapter(context, R.layout.mealplan_layout, foods);
                ListView meals = (ListView) findViewById(R.id.meals);
                meals.setRotation(-90);
                meals.setAdapter(mealPlanAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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

    public void toAddFoodInGroup(View v) {
        Intent intent = new Intent(this, GroupAddFood.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}
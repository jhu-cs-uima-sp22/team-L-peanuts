package com.example.peanuts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupActivity extends AppCompatActivity {
    private String id;
    private SharedPreferences preferences;
    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://peanuts-e9a7c-default-rtdb.firebaseio.com/");
    private DatabaseReference myRef = database.getReference();
    private List<String> foodIds;
    private List<FoodItem> foods;
    private Map<String, List<String>> restrictions;
    private List<NewAccount.User> members;
    private Map<String, FoodItem> foodPosts;
    //private List<String> images;
    //private List<String> foodName;
    //private List<List<String>> allergens;


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
        restrictions = new HashMap<>();
        members = new ArrayList<>();
        foodPosts = new HashMap<>();
        myRef.child("groups").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("ID", id);
                //GroupItem group = (GroupItem) dataSnapshot.getValue();
                String name = (String) dataSnapshot.child("groupName").getValue();
                setTitle(name);
                Log.d("Debug", "Group Name: " + name);
                boolean isHost = preferences.getString("user_email", "").equals((String) dataSnapshot.child("host").getValue());
                foods = new ArrayList<>();
                for (DataSnapshot foodIDs : dataSnapshot.child("foods").getChildren()) {
                    String image = foodIDs.child("imageUri").getValue(String.class);
                    Log.d("Debug", "Image: " + image);
                    String title = foodIDs.child("name").getValue(String.class);
                    Log.d("Debug", "Food Name: " + title);

                    ArrayList<String> allergens = (ArrayList<String>)foodIDs.child("allergens").getValue();
                    Log.d("Debug", "Allergens: " + allergens);
                    foods.add(new FoodItem(title, image, allergens));
                }

                //**FOR TESTING**
                /*boolean[] booleans = new boolean[12];
                booleans[5] = true;
                foods.add(new FoodItem("Spaghetti", booleans, getDrawable(R.drawable.spaghetti)));
                foods.add(new FoodItem("Spaghetti", booleans, getDrawable(R.drawable.spaghetti)));
                foods.add(new FoodItem("Spaghetti", booleans, getDrawable(R.drawable.spaghetti)));
                foods.add(new FoodItem("Spaghetti", booleans, getDrawable(R.drawable.spaghetti)));
                foods.add(new FoodItem("Spaghetti", booleans, getDrawable(R.drawable.spaghetti)));*/
                //**FOR TESTING**

                members = (ArrayList<NewAccount.User>) dataSnapshot.child("members").getValue();
                Log.d("Debug", "Members1: " + String.valueOf(members));

                restrictions = (Map<String, List<String>>) dataSnapshot.child("restrictions").getValue();
                Log.d("Debug", "Restrictions1: " + String.valueOf(restrictions));
                if (isHost) {
                    placeholder.setVisibility(View.INVISIBLE);
                    restrictionsView.setVisibility(View.VISIBLE);
                } else {
                    ImageButton addMealPlan = findViewById(R.id.AddMealPlanButton);
                    addMealPlan.setVisibility(View.INVISIBLE);
                    if (foods.isEmpty()) { //check if meal plan is empty
                        //if meal plan is empty
                        placeholder.setVisibility(View.VISIBLE);
                        response.setVisibility(View.INVISIBLE);
                        restrictionsView.setVisibility(View.INVISIBLE);
                    } else {
                        //if meal plan exists
                        placeholder.setVisibility(View.INVISIBLE);
                        response.setVisibility(View.VISIBLE);
                        restrictionsView.setVisibility(View.INVISIBLE);
                    }
                }
                ConstraintLayout cardView;
                if (restrictions != null) {
                    if (!restrictions.containsKey("Peanuts")) {
                        cardView = (ConstraintLayout) findViewById(R.id.peanuts);
                        cardView.setMaxWidth(0);
                    } else {
                        cardView = (ConstraintLayout) findViewById(R.id.peanuts);
                        cardView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context, Pop.class);
                                intent.putExtra("foodName", "Peanut Allergy");
                                ArrayList<String> temp = new ArrayList<>();
                                for (int i = 0; i < 100; i++) {
                                    temp.add("hello");
                                }
                                intent.putStringArrayListExtra("data", /*(ArrayList<String>) restrictions.get("Peanuts")*/temp);
                                context.startActivity(intent);
                            }
                        });
                    }
                    if (!restrictions.containsKey("Dairy")) {
                        cardView = (ConstraintLayout) findViewById(R.id.dairy);
                        cardView.setMaxWidth(0);
                    } else {
                        cardView = (ConstraintLayout) findViewById(R.id.dairy);
                        cardView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context, Pop.class);
                                intent.putExtra("foodName", "Dairy Allergy");
                                intent.putStringArrayListExtra("data", (ArrayList<String>) restrictions.get("Dairy"));
                                context.startActivity(intent);
                            }
                        });
                    }
                    if (!restrictions.containsKey("Seafood")) {
                        cardView = (ConstraintLayout) findViewById(R.id.seafood);
                        cardView.setMaxWidth(0);
                    } else {
                        cardView = (ConstraintLayout) findViewById(R.id.seafood);
                        cardView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context, Pop.class);
                                intent.putExtra("foodName", "Seafood Allergy");
                                intent.putStringArrayListExtra("data", (ArrayList<String>) restrictions.get("Seafood"));
                                context.startActivity(intent);
                            }
                        });
                    }
                    if (!restrictions.containsKey("Soy")) {
                        cardView = (ConstraintLayout) findViewById(R.id.soy);
                        cardView.setMaxWidth(0);
                    } else {
                        cardView = (ConstraintLayout) findViewById(R.id.soy);
                        cardView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context, Pop.class);
                                intent.putExtra("foodName", "Soy Allergy");
                                intent.putStringArrayListExtra("data", (ArrayList<String>) restrictions.get("Soy"));
                                context.startActivity(intent);
                            }
                        });
                    }
                    if (!restrictions.containsKey("Strawberries")) {
                        cardView = (ConstraintLayout) findViewById(R.id.strawberries);
                        cardView.setMaxWidth(0);
                    } else {
                        cardView = (ConstraintLayout) findViewById(R.id.strawberries);
                        cardView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context, Pop.class);
                                intent.putExtra("foodName", "Strawberry Allergy");
                                intent.putStringArrayListExtra("data", (ArrayList<String>) restrictions.get("Strawberries"));
                                context.startActivity(intent);
                            }
                        });
                    }
                    if (!restrictions.containsKey("Shellfish")) {
                        cardView = (ConstraintLayout) findViewById(R.id.shellfish);
                        cardView.setMaxWidth(0);
                    } else {
                        cardView = (ConstraintLayout) findViewById(R.id.shellfish);
                        cardView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context, Pop.class);
                                intent.putExtra("foodName", "Shellfish Allergy");
                                intent.putStringArrayListExtra("data", (ArrayList<String>) restrictions.get("Shellfish"));
                                context.startActivity(intent);
                            }
                        });
                    }
                    if (!restrictions.containsKey("Eggs")) {
                        cardView = (ConstraintLayout) findViewById(R.id.eggs);
                        cardView.setMaxWidth(0);
                    } else {
                        cardView = (ConstraintLayout) findViewById(R.id.eggs);
                        cardView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context, Pop.class);
                                intent.putExtra("foodName", "Egg Allergy");
                                intent.putStringArrayListExtra("data", (ArrayList<String>) restrictions.get("Eggs"));
                                context.startActivity(intent);
                            }
                        });
                    }
                    if (!restrictions.containsKey("Tree Nuts")) {
                        cardView = (ConstraintLayout) findViewById(R.id.treenuts);
                        cardView.setMaxWidth(0);
                    } else {
                        cardView = (ConstraintLayout) findViewById(R.id.treenuts);
                        cardView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context, Pop.class);
                                intent.putExtra("foodName", "Tree Nut Allergy");
                                intent.putStringArrayListExtra("data", (ArrayList<String>) restrictions.get("Tree Nuts"));
                                context.startActivity(intent);
                            }
                        });
                    }
                    if (!restrictions.containsKey("Wheat")) {
                        cardView = (ConstraintLayout) findViewById(R.id.wheat);
                        cardView.setMaxWidth(0);
                    } else {
                        cardView = (ConstraintLayout) findViewById(R.id.wheat);
                        cardView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context, Pop.class);
                                intent.putExtra("foodName", "Wheat Allergy");
                                intent.putStringArrayListExtra("data", (ArrayList<String>) restrictions.get("Wheat"));
                                context.startActivity(intent);
                            }
                        });
                    }
                    if (!restrictions.containsKey("Gluten")) {
                        cardView = (ConstraintLayout) findViewById(R.id.gluten);
                        cardView.setMaxWidth(0);
                    } else {
                        cardView = (ConstraintLayout) findViewById(R.id.gluten);
                        cardView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context, Pop.class);
                                intent.putExtra("foodName", "Gluten Allergy");
                                intent.putStringArrayListExtra("data", (ArrayList<String>) restrictions.get("Gluten"));
                                context.startActivity(intent);
                            }
                        });
                    }
                    if (!restrictions.containsKey("Avocado")) {
                        cardView = (ConstraintLayout) findViewById(R.id.avocado);
                        cardView.setMaxWidth(0);
                    } else {
                        cardView = (ConstraintLayout) findViewById(R.id.avocado);
                        cardView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context, Pop.class);
                                intent.putExtra("foodName", "Avocado Allergy");
                                intent.putStringArrayListExtra("data", (ArrayList<String>) restrictions.get("Avocado"));
                                context.startActivity(intent);
                            }
                        });
                    }
                    if (!restrictions.containsKey("Sesame")) {
                        cardView = (ConstraintLayout) findViewById(R.id.sesame);
                        cardView.setMaxWidth(0);
                    } else {
                        cardView = (ConstraintLayout) findViewById(R.id.sesame);
                        cardView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context, Pop.class);
                                intent.putExtra("foodName", "Sesame Allergy");
                                intent.putStringArrayListExtra("data", (ArrayList<String>) restrictions.get("Sesame"));
                                context.startActivity(intent);
                            }
                        });
                    }
                }
                Log.d("Debug", "Members: " + String.valueOf(members));
                GroupMemberAdapter memberAdapter = new GroupMemberAdapter(context, R.layout.group_users_layout, members);
                ListView membersList = (ListView) findViewById(R.id.ResponseList);
                Log.d("Debug", String.valueOf(members));
                membersList.setAdapter(memberAdapter);
                registerForContextMenu(membersList);
                memberAdapter.notifyDataSetChanged();

                //populate the meal plan with current foods
                GroupMealPlanAdapter mealPlanAdapter = new GroupMealPlanAdapter(context, foods);
                RecyclerView meals = (RecyclerView) findViewById(R.id.MealPlanList);
                LinearLayoutManager llm = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                meals.setLayoutManager(llm);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(meals.getContext(), llm.getOrientation());
                meals.addItemDecoration(dividerItemDecoration);
                meals.setAdapter(mealPlanAdapter);
                registerForContextMenu(meals);
                mealPlanAdapter.notifyDataSetChanged();
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


    //***the below functions will have to be moved into onclick listeners later***
    public void onCheckClick(View view) {
        ConstraintLayout cl = findViewById(R.id.MealPlanResponse);
        cl.setBackgroundColor(Color.rgb(211, 225,175));
        MaterialButton mb = findViewById(R.id.ResponseCross);
        mb.setVisibility(View.INVISIBLE);
        TextView tv = findViewById(R.id.textView17);
        tv.setVisibility(View.INVISIBLE);
        TextView looksGood = findViewById(R.id.LooksGood);
        looksGood.setVisibility(View.VISIBLE);
        TextView changeResponse = findViewById(R.id.changeResponseCheck);
        changeResponse.setVisibility(View.VISIBLE);
    }

    public void onCrossClick(View view) {
        ConstraintLayout cl = findViewById(R.id.MealPlanResponse);
        cl.setBackgroundColor(Color.rgb(234, 177,175)); //change
        MaterialButton mb = findViewById(R.id.ResponseCheck);
        mb.setVisibility(View.INVISIBLE);
        TextView tv = findViewById(R.id.textView17);
        tv.setVisibility(View.INVISIBLE);
        TextView okThanks = findViewById(R.id.okThanks);
        okThanks.setVisibility(View.VISIBLE);
        TextView changeResponse = findViewById(R.id.changeResponseCross);
        changeResponse.setVisibility(View.VISIBLE);
    }

    public void changeResponse(View view) {
        ConstraintLayout cl = findViewById(R.id.MealPlanResponse);
        cl.setBackgroundColor(Color.rgb(250, 209,169));
        MaterialButton mbCross = findViewById(R.id.ResponseCross);
        mbCross.setVisibility(View.VISIBLE);
        MaterialButton mbCheck = findViewById(R.id.ResponseCheck);
        mbCheck.setVisibility(View.VISIBLE);
        TextView tv = findViewById(R.id.textView17);
        tv.setVisibility(View.VISIBLE);
        TextView looksGood = findViewById(R.id.LooksGood);
        looksGood.setVisibility(View.INVISIBLE);
        TextView changeResponse1 = findViewById(R.id.changeResponseCheck);
        changeResponse1.setVisibility(View.INVISIBLE);
        TextView okThanks = findViewById(R.id.okThanks);
        okThanks.setVisibility(View.INVISIBLE);
        TextView changeResponse2 = findViewById(R.id.changeResponseCross);
        changeResponse2.setVisibility(View.INVISIBLE);
    }
}
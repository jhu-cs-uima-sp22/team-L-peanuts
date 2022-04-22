package com.example.peanuts.ui.profile;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.GridView;

import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peanuts.EditFoods;
import com.example.peanuts.EditRestrictions;
import com.example.peanuts.FoodDetail;
import com.example.peanuts.FoodItem;
import com.example.peanuts.FoodItemAdapter;
import com.example.peanuts.FoodItemAdapterProfile;
import com.example.peanuts.Login;
import com.example.peanuts.MainActivity;
import com.example.peanuts.ProfileViewModel;
import com.example.peanuts.R;

import com.example.peanuts.Settings;
import com.example.peanuts.databinding.FragmentProfileBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import com.example.peanuts.databinding.FragmentProfileBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private MainActivity myact;
    private SharedPreferences myPrefs;
    private GridView myList;
    protected FoodItemAdapterProfile adapter;
    public int foodPosition = 0;
    private String user;
    private ArrayList<String> checkedItem;
    private String name = "";
    protected ArrayList<FoodItem> foodItems;

    private DatabaseReference myRef;
    private DatabaseReference myRefForFoods;
    private FirebaseDatabase database;
    private FirebaseDatabase databaseForFoods;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        myact = (MainActivity) getActivity();

        database = FirebaseDatabase.getInstance("https://peanuts-e9a7c-default-rtdb.firebaseio.com/");
        myRef = database.getReference("users");
        databaseForFoods = FirebaseDatabase.getInstance("https://peanuts-e397e-default-rtdb.firebaseio.com/");
        myRefForFoods = databaseForFoods.getReference("Users");

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Context context = myact.getApplicationContext();
        myPrefs = PreferenceManager.getDefaultSharedPreferences(context);

        this.user = myPrefs.getString("user_email", "");

        myRef.child(user).child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("retrieve", dataSnapshot.toString());
                name = (String) dataSnapshot.getValue();
                    Context context = getContext();
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("user_name", name);
                    editor.apply();
                    TextView name_text = (TextView) root.findViewById(R.id.new_name);
                    name_text.setText(name);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("retrieve", databaseError.toString());
            }
        });

        TextView email_text = (TextView) root.findViewById(R.id.email);
        email_text.setText(myPrefs.getString("user_email", ""));

        myRef.child(user).child("restrictions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    Log.d("retrieve_success", dataSnapshot.toString());
                    checkedItem = (ArrayList<String>) dataSnapshot.getValue();
                } else {
                    checkedItem = new ArrayList<>();
                }
                ConstraintLayout cardView;
                if (!checkedItem.contains("Peanuts")) {
                    cardView = (ConstraintLayout) root.findViewById(R.id.peanuts_card);
                    cardView.setMaxWidth(0);
                }
                if (!checkedItem.contains("Dairy")) {
                    cardView = (ConstraintLayout) root.findViewById(R.id.dairy_card);
                    cardView.setMaxWidth(0);
                }
                if (!checkedItem.contains("Seafood")) {
                    cardView = (ConstraintLayout) root.findViewById(R.id.seafood_card);
                    cardView.setMaxWidth(0);
                }
                if (!checkedItem.contains("Soy")) {
                    cardView = (ConstraintLayout) root.findViewById(R.id.soy_card);
                    cardView.setMaxWidth(0);
                }
                if (!checkedItem.contains("Strawberries")) {
                    cardView = (ConstraintLayout) root.findViewById(R.id.strawberries_card);
                    cardView.setMaxWidth(0);
                }
                if (!checkedItem.contains("Shellfish")) {
                    cardView = (ConstraintLayout) root.findViewById(R.id.shellfish_card);
                    cardView.setMaxWidth(0);
                }
                if (!checkedItem.contains("Eggs")) {
                    cardView = (ConstraintLayout) root.findViewById(R.id.eggs_card);
                    cardView.setMaxWidth(0);
                }
                if (!checkedItem.contains("Tree Nuts")) {
                    cardView = (ConstraintLayout) root.findViewById(R.id.tree_nuts_card);
                    cardView.setMaxWidth(0);
                }
                if (!checkedItem.contains("Wheat")) {
                    cardView = (ConstraintLayout) root.findViewById(R.id.wheat_card);
                    cardView.setMaxWidth(0);
                }
                if (!checkedItem.contains("Gluten")) {
                    cardView = (ConstraintLayout) root.findViewById(R.id.gluten_card);
                    cardView.setMaxWidth(0);
                }
                if (!checkedItem.contains("Avocado")) {
                    cardView = (ConstraintLayout) root.findViewById(R.id.avocado_card);
                    cardView.setMaxWidth(0);
                }
                if (!checkedItem.contains("Sesame")) {
                    cardView = (ConstraintLayout) root.findViewById(R.id.sesame_card);
                    cardView.setMaxWidth(0);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("retrieve_fail", databaseError.toString());
                checkedItem = new ArrayList<>();
            }
        });

        final TextView textView = binding.textProfile;
        profileViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        ImageButton btn = (ImageButton) root.findViewById(R.id.settings_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Settings.class);
                startActivity(intent);
            }
        });
        ImageButton editFoodsbtn = (ImageButton) root.findViewById(R.id.EditFoodsButton);
        editFoodsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditFoods.class);
                startActivity(intent);
            }
        });
        ImageButton editRestrictionsbtn = (ImageButton) root.findViewById(R.id.EditRestrictionsButton);
        editRestrictionsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditRestrictions.class);
                Context context = getContext();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                intent.putExtra("user", preferences.getString("user_email", ""));
                startActivity(intent);
            }
        });

        foodItems = new ArrayList<>();

        myList = (GridView) root.findViewById(R.id.myFoodListProfile);
        myRefForFoods.child(user).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                foodItems = (ArrayList<FoodItem>) dataSnapshot.getValue();
                if (foodItems == null) {
                    foodItems = new ArrayList<>();
                }
                Context context = getContext();
                adapter = new FoodItemAdapterProfile(context, R.layout.food_layout_profile, foodItems, user);
                myList.setAdapter(adapter);
                registerForContextMenu(myList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                foodItems = new ArrayList<>();
            }
        });

        adapter = new FoodItemAdapterProfile(this.getContext(), R.layout.food_layout_profile, myact.foodItems, user);

        myList.setAdapter(adapter);

        registerForContextMenu(myList);

        return root;
    }

    @Override
    public void onResume() {
        View root = binding.getRoot();

        String name = myPrefs.getString("user_name", "");
        String email = myPrefs.getString("user_email", "");
        String password = myPrefs.getString("user_password", "");

        TextView name_text = (TextView) root.findViewById(R.id.new_name);
        name_text.setText(name);

        myRef.child(email).child("name").setValue(name);
        myRef.child(email).child("password").setValue(password);
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
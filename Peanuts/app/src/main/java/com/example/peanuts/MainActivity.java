package com.example.peanuts;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import com.example.peanuts.databinding.ActivityMainBinding;
import com.example.peanuts.ui.add.AddFragment;
import com.example.peanuts.ui.profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FragmentTransaction transaction;
    private Fragment addFood;
    private Fragment profile;
    private SharedPreferences preferences;
    public ArrayList<FoodItem> foodItems;
    public ArrayList<GroupItem> groupItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context context = getApplicationContext();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("0", "Peanuts");
        editor.putString("1", "Dairy");
        editor.putString("2", "Seafood");
        editor.putString("3", "Soy");
        editor.putString("4", "Strawberries");
        editor.putString("5", "Shellfish");
        editor.putString("6", "Eggs");
        editor.putString("7", "Tree Nuts");
        editor.putString("8", "Wheat");
        editor.putString("9", "Gluten");
        editor.putString("10", "Avocado");
        editor.putString("11", "Sesame");
        editor.apply();

        foodItems = new ArrayList<>();
        boolean[] restrictions = new boolean[12];
        restrictions[1] = true;
        restrictions[6] = true;
        restrictions[8] = true;
        restrictions[9] = true;
        Drawable image = getDrawable(R.drawable.spaghetti);
        foodItems.add(new FoodItem("Spaghetti", restrictions, image));

        groupItems = new ArrayList<>();
        ArrayList<NewAccount.User> members = new ArrayList<>();
        groupItems.add(new GroupItem("Birthday Party", members));
        groupItems.add(new GroupItem("Weekly Scrum", members));

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addFood = new AddFragment();
        profile = new ProfileFragment();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

//        if (id == R.id.nav_exit) {
//            this.finishAffinity();  // exit the app
//        }
                if (id == R.id.text_add) {
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_view, addFood);
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else if (id == R.id.navigation_profile) {
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_view, profile);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            return true;
            }
        });
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.text_explore, R.id.text_group, R.id.navigation_notification, R.id.navigation_add, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

}
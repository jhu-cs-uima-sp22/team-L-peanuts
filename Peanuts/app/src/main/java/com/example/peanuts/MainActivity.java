package com.example.peanuts;

import android.os.Bundle;
import android.view.MenuItem;

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

import com.example.peanuts.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FragmentTransaction transaction;
    private Fragment addFood;
    private Fragment profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                if (id == R.id.add_food) {
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
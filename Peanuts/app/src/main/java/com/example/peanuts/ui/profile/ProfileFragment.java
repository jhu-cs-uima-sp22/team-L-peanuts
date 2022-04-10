package com.example.peanuts.ui.profile;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.peanuts.EditFoods;
import com.example.peanuts.EditRestrictions;
import com.example.peanuts.FoodDetail;
import com.example.peanuts.FoodItemAdapterProfile;
import com.example.peanuts.MainActivity;
import com.example.peanuts.ProfileViewModel;
import com.example.peanuts.R;
import com.example.peanuts.Settings;
import com.example.peanuts.databinding.FragmentProfileBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private MainActivity myact;
    private SharedPreferences myPrefs;
    private GridView myList;
    protected FoodItemAdapterProfile adapter;
    public int foodPosition = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        myact = (MainActivity) getActivity();

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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
                startActivity(intent);
            }
        });

        Context context = myact.getApplicationContext();
        myPrefs = PreferenceManager.getDefaultSharedPreferences(context);

        myList = (GridView) root.findViewById(R.id.myFoodListProfile);

        adapter = new FoodItemAdapterProfile(this.getContext(), R.layout.food_layout_profile, myact.foodItems);

        myList.setAdapter(adapter);

        registerForContextMenu(myList);

        adapter.notifyDataSetChanged();

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                foodPosition = position;
                Snackbar.make(view, "Selected #" + id, Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                // create intent to launch ItemDetail activity with the item's details
                // startActivity for result so that you know the status of the toggle button when you return
                Intent intent = new Intent(myact, FoodDetail.class);
                String name = myact.foodItems.get(foodPosition).getName();
                boolean[] restrictions = myact.foodItems.get(foodPosition).getRestrictions();
                intent.putExtra("name", name);
                intent.putExtra("restrictions", restrictions);
                intent.putExtra("image", R.drawable.spaghetti);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        View root = binding.getRoot();
        TextView name = (TextView) root.findViewById(R.id.name);
        name.setText(myPrefs.getString("NAME", "Karen Smith"));
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
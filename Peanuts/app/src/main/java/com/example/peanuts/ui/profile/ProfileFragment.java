package com.example.peanuts.ui.profile;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.peanuts.EditRestrictions;
import com.example.peanuts.FoodDetail;
import com.example.peanuts.MainActivity;
import com.example.peanuts.R;
import com.example.peanuts.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private MainActivity myact;
    private SharedPreferences myPrefs;

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
        ImageButton foodbtn = (ImageButton) root.findViewById(R.id.Spaghetti);
        foodbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FoodDetail.class);
                intent.putExtra("name", "Spaghetti");
                intent.putExtra("image", R.drawable.spaghetti);
                intent.putExtra("allergens", "-Dairy\n-Eggs\n-Wheat\n-Gluten\n");
                startActivity(intent);
            }
        });

        Context context = myact.getApplicationContext();
        myPrefs = PreferenceManager.getDefaultSharedPreferences(context);

        return root;
    }

    @Override
    public void onResume() {
        View root = binding.getRoot();
        TextView name = (TextView) root.findViewById(R.id.new_name);
        name.setText(myPrefs.getString("NAME", "Karen Smith"));
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
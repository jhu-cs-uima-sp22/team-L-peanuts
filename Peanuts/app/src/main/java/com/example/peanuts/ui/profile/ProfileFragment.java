package com.example.peanuts.ui.profile;

import static android.widget.Toast.LENGTH_SHORT;

import androidx.lifecycle.ViewModelProvider;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peanuts.MainActivity;
import com.example.peanuts.R;
import com.example.peanuts.databinding.FragmentAddBinding;
import com.example.peanuts.databinding.FragmentProfileBinding;
import com.example.peanuts.ui.add.AddViewModel;

import org.w3c.dom.Text;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private MainActivity myact;

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
                Intent intent = new Intent(getActivity(), EditFoods.class); //TODO: this needs to be changed to the edit restrictions activity
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
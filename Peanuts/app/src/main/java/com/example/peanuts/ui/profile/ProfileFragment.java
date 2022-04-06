package com.example.peanuts.ui.profile;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
        return root;
    }

    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), Settings.class);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
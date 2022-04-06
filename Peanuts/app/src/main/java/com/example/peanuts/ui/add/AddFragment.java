package com.example.peanuts.ui.add;



import static android.widget.Toast.LENGTH_SHORT;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peanuts.R;
import com.example.peanuts.databinding.FragmentAddBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.example.peanuts.databinding.FragmentNotificationsBinding;
import com.example.peanuts.ui.notifications.NotificationsViewModel;
import com.example.peanuts.ui.profile.ProfileFragment;


import java.util.List;

public class AddFragment extends Fragment {

    private FragmentAddBinding binding;
    private Button save;
    private Button cancel;
    private FragmentTransaction transaction;

    private boolean mTwoPane;
    private String userId;
    private List<String> mItems;
    private RecyclerView.RecyclerListener mAdapt;

   /* @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = this.getActivity();
        //not sure what to do with recyclerView

        mItems.add("Peanuts");
        mItems.add("Dairy");
        mItems.add("Tree nuts");
        mItems.add("Shellfish");
        mItems.add("Seafood");
        mItems.add("Strawberry");
        mItems.add("Soy");
        mItems.add("Gluten");
        mItems.add("Eggs");
    }*/

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //AddViewModel addViewModel =
                //new ViewModelProvider(this).get(AddViewModel.class);

        View view = inflater.inflate(R.layout.fragment_add, container, false);

        binding = FragmentAddBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textAdd;

        save = (Button) view.findViewById(R.id.save_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create post
                //save data in database
                //transaction = getSupportFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack so the user can navigate back
                //transaction.replace(R.id.fragment_container, this.item);
                //transaction.addToBackStack(null);
            }
        });
        cancel = (Button) view.findViewById(R.id.cancel_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
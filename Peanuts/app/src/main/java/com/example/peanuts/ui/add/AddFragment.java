package com.example.peanuts.ui.add;



import static android.widget.Toast.LENGTH_SHORT;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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
import com.example.peanuts.ui.profile.EditFoods;
import com.example.peanuts.ui.profile.ProfileFragment;


import java.util.List;

public class AddFragment extends Fragment {

    private FragmentAddBinding binding;
    private Button save;
    private Button clear;
    private Button addAllergens;
    private EditText name;
    private String nameOfFood;
    private FragmentTransaction transaction;

    private boolean mTwoPane;
    private String userId;
    private List<String> mItems;
    private RecyclerView.RecyclerListener mAdapt;
    int REQUEST_CODE;
    int RESULT_CODE;

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

        name = (EditText) root.findViewById(R.id.item_text);
        name.setHint("Name of food");
        save = (Button) root.findViewById(R.id.save_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create post
                //save data in database
                Context context = getContext();
                int duration = Toast.LENGTH_SHORT;
                nameOfFood = name.getText().toString();
                CharSequence text;
                if (nameOfFood.equals("")) {
                    text = "You need to name your food";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {
                    text = "Food Added!";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });
        clear = (Button) root.findViewById(R.id.clear_button);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "Name of food";
                name.setText("");
                name.setHint("Name of food");
            }
        });

        addAllergens = (Button) root.findViewById(R.id.add_allergens);
        addAllergens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditFoods.class);
                startActivity(intent);
                //NEED TO FIGURE OUT HOW TO LAUNCH WITH RESULT

                //getActivity().startActivityForResult(intent, 1);
                //getActivity().startActivityForResult(intent, REQUEST_CODE);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_CODE) {
            //String testResult = data.getStringExtra(EXTRA_KEY_TEST);
            // TODO: Do something with your extra data
        }
    }*/
}
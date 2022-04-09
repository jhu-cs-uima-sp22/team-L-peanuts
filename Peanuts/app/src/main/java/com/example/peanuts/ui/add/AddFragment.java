package com.example.peanuts.ui.add;

import static android.widget.Toast.LENGTH_SHORT;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.peanuts.Item;
import com.example.peanuts.ItemAdapter;
import com.example.peanuts.R;
import com.example.peanuts.databinding.FragmentAddBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.example.peanuts.databinding.FragmentNotificationsBinding;
import com.example.peanuts.ui.notifications.NotificationsViewModel;
import com.example.peanuts.ui.profile.EditFoods;
import com.example.peanuts.ui.profile.ProfileFragment;


import java.util.ArrayList;
import com.example.peanuts.MainActivity;
import com.example.peanuts.R;
import com.example.peanuts.databinding.FragmentAddBinding;
import com.example.peanuts.ui.profile.ProfileFragment;

import java.util.List;

public class AddFragment extends Fragment implements View.OnClickListener{

    private FragmentAddBinding binding;
    private Button save;
    private Button clear;
    private Button addAllergens;
    private EditText name;
    private String nameOfFood;
    private ListView listView;
    private ItemAdapter adapter;
    protected List<Item> myItems;
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
        Log.d("FRAG", "addFood");

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
                    name.setText("");
                    name.setHint("Name of food");
                }
            }
        });
        clear = (Button) root.findViewById(R.id.clear_button);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setText("");
                name.setHint("Name of food");
            }
        });

        //allergen list

        /*listView = (ListView) root.findViewById(R.id.list);
        myItems = new ArrayList<>();
        myItems.add(new Item("Peanut", false));
        myItems.add(new Item("Dairy", false));

        adapter = new ItemAdapter(getContext(), R.layout.fragment_add, myItems);
        Log.d("debug", "after a initiated");
        listView.setAdapter(adapter);
        Log.d("debug", "set");*/

        //addAllergens = (Button) root.findViewById(R.id.add_allergens);
       /* addAllergens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TRYING TO LAUNCH ACTIVITY WITH RETURN
               // Intent intent = new Intent(getActivity(), EditFoods.class);
                //registerForActivityResult(intent, intent);
                //startActivity(intent);
                //NEED TO FIGURE OUT HOW TO LAUNCH WITH RESULT

                //getActivity().startActivityForResult(intent, 1);
                //getActivity().startActivityForResult(intent, REQUEST_CODE);

                //TRYING TO USE LIST
                //scrollView = (ListView)root.findViewById(R.id.listView);
                //scrollView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

            }
        });*/

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
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onClick(View view) {
        Log.d("Save", "save");
        //create post
        //save data in database
        //transaction = getSupportFragmentManager().beginTransaction();

        Fragment fragment = new ProfileFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_view, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack so the user can navigate back
        //transaction.replace(R.id.fragment_container, this.item);
        //transaction.addToBackStack(null);
    }
}
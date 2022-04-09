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
import android.graphics.drawable.Icon;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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
    private Button image;
    private EditText name;
    private String nameOfFood;
    private ImageView imageView;
    private ImageView imageView2;
    private boolean setImage;
    private ActivityResultLauncher<String> getContent;

    protected ArrayList<Item> restrictions;
    protected ItemAdapter adapter;

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
        image = root.findViewById(R.id.pick_image);
        imageView = root.findViewById(R.id.imageView);
        imageView2 = root.findViewById(R.id.imageView2);
        setImage = false;

        //allergen list


        // create temp ArrayList of items
        restrictions = new ArrayList<>();
        restrictions.add(new Item("peanuts", false));
        restrictions.add(new Item("dairy", false));
        restrictions.add(new Item("seafood", false));
        restrictions.add(new Item("soy", false));
        restrictions.add(new Item("strawberries", false));
        restrictions.add(new Item("shellfish", false));
        restrictions.add(new Item("eggs", false));
        restrictions.add(new Item("tree nuts", false));
        restrictions.add(new Item("wheat", false));
        restrictions.add(new Item("gluten", false));
        restrictions.add(new Item("avocado", false));
        restrictions.add(new Item("sesame", false));

        adapter = new ItemAdapter(getContext(), R.layout.item_restriction, restrictions);

        ListView myList = (ListView) root.findViewById(R.id.list);
        myList.setAdapter(adapter);
        registerForContextMenu(myList);
        // refresh view
        adapter.notifyDataSetChanged();

        save = (Button) root.findViewById(R.id.save_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create post
                //save data in database
                Context context = getContext();
                int duration = Toast.LENGTH_LONG;
                nameOfFood = name.getText().toString();
                CharSequence text;
                if (!setImage) {
                    text = "You need to pick an image";
                }
                else if (nameOfFood.equals("")) {
                    text = "You need to name your food";
                } else {
                    text = "Food Added!";
                    clearContent();
                }

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
        clear = (Button) root.findViewById(R.id.clear_button);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearContent();
            }
        });

        //IMAGE



        getContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                imageView.setImageURI(result);
                setImage = true;
                imageView2.setVisibility(View.INVISIBLE);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContent.launch("image/*");
            }
        });

        return root;
    }

    private void clearContent () {
        name.setText("");
        name.setHint("Name of food");

        for(int i = 0; i < restrictions.size(); i++) {
            if (restrictions.get(i).isChecked()) {
                restrictions.get(i).changeChecked(false);
            }
        }
        adapter.notifyDataSetChanged();
        imageView.setImageResource(android.R.color.transparent);
        imageView2.setVisibility(View.VISIBLE);
        setImage = false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

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
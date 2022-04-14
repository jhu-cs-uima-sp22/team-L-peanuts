package com.example.peanuts.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.peanuts.FoodItem;
import com.example.peanuts.Item;
import com.example.peanuts.MainActivity;
import com.example.peanuts.R;
import com.example.peanuts.databinding.FragmentExploreBinding;
import com.example.peanuts.ui.add.FoodPost;
import com.example.peanuts.ui.add.FoodPostAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ExploreFragment extends Fragment {

    private FragmentExploreBinding binding;
    private ListView list;
    private CardView card;
    private ImageView image;
    private TextView title;
    private PostAdapter adapter;
    private ArrayList<FoodItem> usersPost;

    protected FirebaseDatabase database;
    protected DatabaseReference myRef;
    private SharedPreferences sp;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ExploreViewModel homeViewModel =
                new ViewModelProvider(this).get(ExploreViewModel.class);

        binding = FragmentExploreBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textExplore;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        list = root.findViewById(R.id.explore_posts);
        usersPost = new ArrayList<>();
        Context context = getContext();
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        String email = sp.getString("user_email", "email");
        //Fill arraylist
        database = FirebaseDatabase.getInstance("https://peanuts-e397e-default-rtdb.firebaseio.com/");
        myRef = database.getReference("Users");

        myRef.child(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("debug", "in onDataChange");

                if (dataSnapshot.getValue() != null) {
                    Log.d("retrieve_success", dataSnapshot.toString());
                    for (DataSnapshot posts: dataSnapshot.getChildren()) {
                        usersPost.add(posts.getValue(FoodItem.class));
                        Log.d("debug", "added child");
                    }

                    //usersPost.remove(usersPost.size()-1);

                } else {
                    usersPost = new ArrayList<>();
                    Log.d("debug", "in empty");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("retrieve_fail", databaseError.toString());
            }
        });

        adapter = new PostAdapter(getContext(), R.layout.explore_posts, usersPost);
        list.setAdapter(adapter);
        registerForContextMenu(list);
        // refresh view
        adapter.notifyDataSetChanged();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
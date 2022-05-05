package com.example.peanuts.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.peanuts.FoodItem;
import com.example.peanuts.R;
import com.example.peanuts.databinding.FragmentExploreBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ExploreFragment extends Fragment {

    private FragmentExploreBinding binding;
    private ListView list;
    private PostAdapter adapter;
    private ArrayList<FoodItem> usersPost;

    protected FirebaseDatabase database;
    protected DatabaseReference myRef;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ExploreViewModel homeViewModel =
                new ViewModelProvider(this).get(ExploreViewModel.class);

        binding = FragmentExploreBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textExplore;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        usersPost = new ArrayList<>();
        Context context = getContext();
        database = FirebaseDatabase.getInstance("https://peanuts-e397e-default-rtdb.firebaseio.com/");
        myRef = database.getReference();

        initSearch(root);
       myRef.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() != null) {
                    for (DataSnapshot user: dataSnapshot.getChildren()) {
                        for (DataSnapshot post: user.getChildren()) {
                            usersPost.add(post.getValue(FoodItem.class));

                            if(getContext() != null && usersPost != null) {

                                adapter = new PostAdapter(getContext(), R.layout.explore_posts, usersPost);
                            }
                            list = root.findViewById(R.id.posts_list);
                            list.setAdapter(adapter);
                            registerForContextMenu(list);
                            // refresh view
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void initSearch (View root) {
       SearchView searchView = (SearchView) root.findViewById(R.id.searchView);

       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String s) {
               return false;
           }

           @Override
           public boolean onQueryTextChange(String s) {
               ArrayList<FoodItem> filteredPosts = new ArrayList<>();
               for (FoodItem post: usersPost) {
                   if (post.getName().toLowerCase().contains(s.toLowerCase())) {
                       filteredPosts.add(post);
                   }
               }

               PostAdapter filteredAdapter = new PostAdapter(getContext(), R.layout.explore_posts, filteredPosts);
               if (list != null) {
                   list.setAdapter(filteredAdapter);
               }
               return false;
           }
       });
    }
}
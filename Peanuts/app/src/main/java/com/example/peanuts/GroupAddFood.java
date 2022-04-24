package com.example.peanuts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.peanuts.ui.home.PostAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GroupAddFood extends AppCompatActivity {

    protected FirebaseDatabase databasePosts;
    protected DatabaseReference myRefPosts;
    protected FirebaseDatabase databaseGroups;
    protected DatabaseReference myRefGroups;
    private ArrayList<FoodItem> usersPost;
    private TextView title;
    private Icon icon;
    private GroupAddFoodAdapter adapter;
    private ListView list;
    protected Context context;
    private Button done;
    private ArrayList<FoodItem> addedItems;
    //protected SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_add_food);
        Intent intent = getIntent();
        setTitle("Add Item");

        context = getApplicationContext();
        usersPost = new ArrayList<>();
        //Fill arraylist
        databasePosts = FirebaseDatabase.getInstance("https://peanuts-e397e-default-rtdb.firebaseio.com/");
        myRefPosts = databasePosts.getReference();
        addedItems = new ArrayList<>();

        initSearch();
        myRefPosts.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("debug", "in onDataChange");

                if (dataSnapshot.getValue() != null) {
                    Log.d("retrieve_success", dataSnapshot.toString());
                    for (DataSnapshot user: dataSnapshot.getChildren()) {
                        for (DataSnapshot post: user.getChildren()) {
                            usersPost.add(post.getValue(FoodItem.class));
                            Log.d("debug", "added child in frag");

                            if(context != null && usersPost != null) {

                                adapter = new GroupAddFoodAdapter(context, R.layout.item_restriction, usersPost, addedItems);
                            }
                            list = findViewById(R.id.item_list);
                            Log.d("debug", "set list");
                            list.setAdapter(adapter);
                            Log.d("debug", "set adapter");
                            registerForContextMenu(list);
                            // refresh view
                            adapter.notifyDataSetChanged();
                            Log.d("debug", "done");
                        }
                    }
                } else {
                    Log.d("debug", "in empty");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("retrieve_fail", databaseError.toString());
            }
        });

        done = (Button) findViewById(R.id.done_button);
        done.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //add addedItems to group database
                finish();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initSearch () {
        SearchView searchView = (SearchView) findViewById(R.id.searchView);

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

                GroupAddFoodAdapter filteredAdapter = new GroupAddFoodAdapter(context, R.layout.item_restriction, filteredPosts, addedItems);
                if (list != null) {
                    list.setAdapter(filteredAdapter);
                }
                return false;
            }
        });
    }
}
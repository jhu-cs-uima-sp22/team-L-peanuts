package com.example.peanuts.ui.groups;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.peanuts.FoodDetail;
import com.example.peanuts.FoodItemAdapterProfile;
import com.example.peanuts.GroupActivity;
import com.example.peanuts.GroupItem;
import com.example.peanuts.GroupItemAdapter;
import com.example.peanuts.MainActivity;
import com.example.peanuts.R;
import com.example.peanuts.databinding.FragmentGroupBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GroupsFragment extends Fragment {

    private FragmentGroupBinding binding;
    private MainActivity myact;
    private ListView myList;
    protected GroupItemAdapter adapter;
    private SharedPreferences preferences;
    public ArrayList<GroupItem> groupItems;
    public ArrayList<String> groupIds;

    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://peanuts-e9a7c-default-rtdb.firebaseio.com/");
    private DatabaseReference myRef = database.getReference();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GroupsViewModel dashboardViewModel =
                new ViewModelProvider(this).get(GroupsViewModel.class);

        myact = (MainActivity) getActivity();

        Context context = getContext();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        binding = FragmentGroupBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        myList = (ListView) root.findViewById(R.id.groups_list);
        groupItems = new ArrayList<>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groupIds = (ArrayList<String>) dataSnapshot.child("users").child(preferences.getString("user_email", "")).child("groups").getValue();
                for (String id : groupIds) {
                    groupItems.add((GroupItem) dataSnapshot.child("groups").child(id).getValue());
                }
                adapter = new GroupItemAdapter(context, R.layout.group_layout, groupItems);
                myList.setAdapter(adapter);
                registerForContextMenu(myList);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                groupIds = new ArrayList<>();
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
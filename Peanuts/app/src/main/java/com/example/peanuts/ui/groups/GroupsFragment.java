package com.example.peanuts.ui.groups;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.peanuts.GroupItemAdapter;
import com.example.peanuts.MainActivity;
import com.example.peanuts.R;
import com.example.peanuts.databinding.FragmentGroupBinding;
import com.google.android.material.snackbar.Snackbar;

public class GroupsFragment extends Fragment {

    private FragmentGroupBinding binding;
    private MainActivity myact;
    private ListView myList;
    protected GroupItemAdapter adapter;
    public int groupPosition = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GroupsViewModel dashboardViewModel =
                new ViewModelProvider(this).get(GroupsViewModel.class);

        myact = (MainActivity) getActivity();

        binding = FragmentGroupBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        myList = (ListView) root.findViewById(R.id.groups_list);

        adapter = new GroupItemAdapter(this.getContext(), R.layout.group_layout, myact.groupItems);

        myList.setAdapter(adapter);

        registerForContextMenu(myList);

        adapter.notifyDataSetChanged();

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                groupPosition = position;
                //Snackbar.make(view, "Selected #" + id, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                // create intent to launch ItemDetail activity with the item's details
                // startActivity for result so that you know the status of the toggle button when you return

                //***NEED TO CHANGE BELOW***
                Intent intent = new Intent(myact, GroupActivity.class);
                //String name = myact.foodItems.get(groupPosition).getName();
                //boolean[] restrictions = myact.foodItems.get(groupPosition).getRestrictions();
                //intent.putExtra("name", name);
                //intent.putExtra("restrictions", restrictions);
                //intent.putExtra("image", R.drawable.spaghetti);
                intent.putExtra("name", myact.groupItems.get(groupPosition).getGroupName());
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
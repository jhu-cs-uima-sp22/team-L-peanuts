package com.example.peanuts.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.peanuts.GroupItem;
import com.example.peanuts.GroupItemAdapter;
import com.example.peanuts.NewAccount;
import com.example.peanuts.R;
import com.example.peanuts.databinding.FragmentNotificationsBinding;
import com.example.peanuts.ui.add.FoodPostAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private ArrayList<NotificationItem> notifications;
    private NotificationsAdapter adapter;

    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://peanuts-e9a7c-default-rtdb.firebaseio.com/");
    private DatabaseReference myRef = database.getReference("users");

    private String user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        Context context = getActivity().getApplicationContext();
        SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.user = myPrefs.getString("user_email", "");

        notifications = new ArrayList<NotificationItem>();

        myRef.child(user).child("notifications").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot notification : dataSnapshot.getChildren()) {
                    Log.d("NOTIF", String.valueOf(notification));
                    if (notification.child("groupName").getValue() != null && notification.child("groupInvite").getValue() != null) {
                        String name = notification.child("groupName").getValue().toString();
                        String invite = notification.child("groupInvite").getValue().toString();
                        String id = notification.getKey().toString();
                        NotificationItem notif = new NotificationItem(name, invite, id, "");
                        notifications.add(notif);
                    }
                }
                adapter = new NotificationsAdapter(getContext(), R.layout.notifications_layout, notifications);

                ListView myList = (ListView) root.findViewById(R.id.notifications);
                myList.setAdapter(adapter);
                registerForContextMenu(myList);
                // refresh view
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                notifications = new ArrayList<>();
            }
        });

        /*ImageView accept = (ImageView) root.findViewById(R.id.group_accept);
        ImageView decline = (ImageView) root.findViewById(R.id.group_decline);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //add to database
                notifications.remove(it);
            }
        });

        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifications.remove(it);
            }
        });

        changeGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to group page
                notifications.remove(it);
            }
        });*/

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
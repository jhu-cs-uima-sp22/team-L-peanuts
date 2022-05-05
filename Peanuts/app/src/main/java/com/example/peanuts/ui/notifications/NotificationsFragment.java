package com.example.peanuts.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.peanuts.R;
import com.example.peanuts.databinding.FragmentNotificationsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

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
                    if (notification.child("groupName").getValue() != null && notification.child("groupInvite").getValue() != null) {
                        String name = Objects.requireNonNull(notification.child("groupName").getValue()).toString();
                        String invite = Objects.requireNonNull(notification.child("groupInvite").getValue()).toString();
                        String id = Objects.requireNonNull(notification.child("group").getValue()).toString();
                        String date = Objects.requireNonNull(notification.child("date").getValue()).toString();
                        NotificationItem notif = new NotificationItem(name, invite, id, date);
                        notifications.add(notif);
                    }
                }
                Collections.sort(notifications, new Comparator<NotificationItem>() {
                    @Override
                    public int compare(NotificationItem i1, NotificationItem i2) {
                        String[] date1 = i1.getDate().split(" ");
                        String[] date2 = i2.getDate().split(" ");
                        String[] time1 = date1[3].split(":");
                        String[] time2 = date2[3].split(":");
                        int year1 = Integer.parseInt(date1[5]);
                        int year2 = Integer.parseInt(date2[5]);
                        int month1 = getMonth(date1[1]);
                        int month2 = getMonth(date2[1]);
                        int day1 = Integer.parseInt(date1[2]);
                        int day2 = Integer.parseInt(date2[2]);
                        int hour1 = Integer.parseInt(time1[0]);
                        int hour2 = Integer.parseInt(time2[0]);
                        int minute1 = Integer.parseInt(time1[1]);
                        int minute2 = Integer.parseInt(time2[1]);
                        int second1 = Integer.parseInt(time1[2]);
                        int second2 = Integer.parseInt(time2[2]);

                        if (year1 != year2) {
                            return year2 - year1;
                        } else if (month1 != month2) {
                            return month2 - month1;
                        } else if (day1 != day2) {
                            return day2 - day1;
                        } else if (hour1 != hour2) {
                            return hour2 - hour1;
                        } else if (minute1 != minute2) {
                            return minute2 - minute1;
                        } else if (second1 != second2) {
                            return second2 - second1;
                        } else {
                            return 0;
                        }
                    }

                    private int getMonth(String month) {
                        switch (month) {
                            case "Jan":
                                return 1;
                            case "Feb":
                                return 2;
                            case "Mar":
                                return 3;
                            case "Apr":
                                return 4;
                            case "May":
                                return 5;
                            case "Jun":
                                return 6;
                            case "Jul":
                                return 7;
                            case "Aug":
                                return 8;
                            case "Sep":
                                return 9;
                            case "Oct":
                                return 10;
                            case "Nov":
                                return 11;
                            default:
                                return 12;
                        }
                    }
                });

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

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
package com.example.peanuts.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.peanuts.R;
import com.example.peanuts.databinding.FragmentNotificationsBinding;
import com.example.peanuts.ui.add.FoodPostAdapter;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private ArrayList<NotificationItem> notifications;
    private NotificationsAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        notifications = new ArrayList<>();
        notifications.add(new NotificationItem("Group1", true, "owner1"));
        notifications.add(new NotificationItem("Group2", true, "owner2"));
        notifications.add(new NotificationItem("Group3", true, "owner3"));
        notifications.add(new NotificationItem("Group4", false));
        notifications.add(new NotificationItem("Group5", true, "owner5"));
        notifications.add(new NotificationItem("Group6", false));

        adapter = new NotificationsAdapter(getContext(), R.layout.notifications_layout, notifications);

        ListView myList = (ListView) root.findViewById(R.id.notifications);
        myList.setAdapter(adapter);
        registerForContextMenu(myList);
        // refresh view
        adapter.notifyDataSetChanged();

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
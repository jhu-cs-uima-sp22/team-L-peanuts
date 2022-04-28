package com.example.peanuts.ui.notifications;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.peanuts.ui.groups.GroupActivity;
import com.example.peanuts.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class NotificationsAdapter extends ArrayAdapter<NotificationItem> {

    int resource;
    List<NotificationItem> notifications;
    //private FirebaseDatabase database = FirebaseDatabase.getInstance("https://peanuts-e9a7c-default-rtdb.firebaseio.com/");
    //private DatabaseReference myRef = database.getReference();

    public NotificationsAdapter(Context ctx, int res, List<NotificationItem> notifications) {
        super(ctx, res, notifications);
        resource = res;
        this.notifications = notifications;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout itemView;
        NotificationItem it = getItem(position);

        if (convertView == null) {
            itemView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(inflater);
            vi.inflate(resource, itemView, true);
        } else {
            itemView = (LinearLayout) convertView;
        }

        TextView message = (TextView) itemView.findViewById(R.id.notification);
        TextView changeGroup = (TextView) itemView.findViewById(R.id.menu_change);
        TextView owner = (TextView) itemView.findViewById(R.id.owner_text);
        TextView date = (TextView) itemView.findViewById(R.id.date);

        if (it.isGroupInvite()) { //new group notification
            message.setText("New Group Invite!");
            owner.setText(it.getOwner());
        } else {
            String str = "Menu Change!";
            message.setText(str);
            owner.setVisibility(View.INVISIBLE);
        }
        changeGroup.setText(it.getGroupName());
        date.setText(it.getDate());

//        Context context = getContext();
//        int duration = Toast.LENGTH_SHORT;
//
//        accept.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //add to database
//                CharSequence text;
//                text = "Joined group " + it.getGroupName();
//
//                notifications.remove(it);
//                notifyDataSetChanged();
//
//                Toast toast = Toast.makeText(context, text, duration);
//                toast.show();
//            }
//        });
//
//        decline.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                CharSequence text;
//                text = "Declined group " + it.getGroupName();
//
//                notifications.remove(it);
//                notifyDataSetChanged();
//                Toast toast = Toast.makeText(context, text, duration);
//                toast.show();
//            }
//        });

        changeGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifications.remove(it);
                notifyDataSetChanged();
                Context context = getContext();
                Intent intent = new Intent(context, GroupActivity.class);
                intent.putExtra("id", it.getGroup());


                Log.d("Debug", "Notifications ID: " + it.getGroup());
                context.startActivity(intent);
            }
        });

        return itemView;
    }
}

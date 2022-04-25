package com.example.peanuts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.io.Serializable;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;

public class GroupItemAdapter extends ArrayAdapter<GroupItem> {

    int resource;

    public GroupItemAdapter(Context ctx, int res, List<GroupItem> items) {
        super(ctx, res, items);
        resource = res;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout groupItemView;
        GroupItem it = getItem(position);

        if (convertView == null) {
            groupItemView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(inflater);
            vi.inflate(resource, groupItemView, true);
        } else {
            groupItemView = (LinearLayout) convertView;
        }

        TextView groupName = (TextView) groupItemView.findViewById(R.id.group_name);
        TextView memberCount = (TextView) groupItemView.findViewById(R.id.member_count);
        Context context = getContext();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (it.getHost() != null && it.getHost().equals(preferences.getString("user_email", "")))
            groupName.setText(it.getGroupName() + " (Host)");
        else
            groupName.setText(it.getGroupName());
        if (it.getMembers() != null) {
            memberCount.setText("" + it.getMembers().size() + " members");
        } else {
            memberCount.setText("0 members");
        }

        CardView groupCard = (CardView) groupItemView.findViewById(R.id.group_card);
        groupCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //***NEED TO CHANGE BELOW*** ADD all the data needed and pass it
                Context context = getContext();
                Intent intent = new Intent(context, GroupActivity.class);

                intent.putExtra("name", it.getGroupName());
                intent.putExtra("isHost", it.getHost().equals(preferences.getString("user_email", "")));
                Bundle args = new Bundle();
                args.putSerializable("foods", (Serializable) it.getFoods());
                args.putSerializable("restrictions", (Serializable) it.getRestrictions());
                args.putSerializable("members", (Serializable) it.getMembers());
                intent.putExtra("bundle", args);
<<<<<<< HEAD

=======
                intent.putExtra("id", it.getId());
>>>>>>> main
                context.startActivity(intent);
            }
        });

        return groupItemView;
    }
}

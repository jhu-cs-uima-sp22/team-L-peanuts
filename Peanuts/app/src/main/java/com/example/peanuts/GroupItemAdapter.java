package com.example.peanuts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.security.acl.Group;
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
        if (it.isHost())
            groupName.setText(it.getGroupName() + " (Host)");
        else
            groupName.setText(it.getGroupName());
        memberCount.setText("" + it.getMembers().size() + " members");
        return groupItemView;
    }
}

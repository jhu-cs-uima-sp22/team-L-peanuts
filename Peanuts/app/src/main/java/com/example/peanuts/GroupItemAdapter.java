package com.example.peanuts;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

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

        CardView groupCard = (CardView) groupItemView.findViewById(R.id.group_card);
        groupCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //***NEED TO CHANGE BELOW*** ADD all the data needed and pass it
                Context context = getContext();
                Intent intent = new Intent(context, GroupActivity.class);
                //String name = myact.foodItems.get(groupPosition).getName();
                //boolean[] restrictions = myact.foodItems.get(groupPosition).getRestrictions();
                //intent.putExtra("name", name);
                //intent.putExtra("restrictions", restrictions);
                //intent.putExtra("image", R.drawable.spaghetti);
                intent.putExtra("isHost", it.isHost());
                intent.putExtra("name", it.getGroupName());
                context.startActivity(intent);
            }
        });

        return groupItemView;
    }
}

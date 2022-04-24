package com.example.peanuts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GroupMemberAdapter extends ArrayAdapter<NewAccount.User> {

    int resource;

    ArrayList<NewAccount.User> members = new ArrayList<>();

    public GroupMemberAdapter(Context ctx, int res, List<NewAccount.User> member) {
        super(ctx, res, member);
        resource = res;
        this.members.addAll(member);
        Log.d("MEMBERS", String.valueOf(members));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout groupMemberView;
        Log.d("IT", String.valueOf(getItem(position)));

        if (convertView == null) {
            groupMemberView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(inflater);
            vi.inflate(resource, groupMemberView, true);
        } else {
            groupMemberView = (LinearLayout) convertView;
        }

        TextView memberName = (TextView) groupMemberView.findViewById(R.id.UserName);
        TextView memberEmail = (TextView) groupMemberView.findViewById(R.id.UserEmail);
        ImageView memberImage = (ImageView) groupMemberView.findViewById(R.id.UserImage);

        Map<String, String> user = (Map<String, String>) getItem(position);

        String name = user.get("name");
        String email = user.get("email");

        memberName.setText(name);
        memberEmail.setText(email);
        Context context = getContext();
        memberImage.setImageDrawable(context.getDrawable(R.drawable.baseline_account_circle_24));

        return groupMemberView;
    }

}
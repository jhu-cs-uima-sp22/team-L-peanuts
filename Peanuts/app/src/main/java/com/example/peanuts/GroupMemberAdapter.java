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

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GroupMemberAdapter extends ArrayAdapter<NewAccount.User> {

    int resource;
    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://peanuts-e9a7c-default-rtdb.firebaseio.com/");
    private DatabaseReference myRef = database.getReference();
    private String path;

    ArrayList<NewAccount.User> members = new ArrayList<>();

    public GroupMemberAdapter(Context ctx, int res, List<NewAccount.User> member) {
        super(ctx, res, member);
        resource = res;
        Log.d("Debug", "in group member adapter");
        if (member != null) {
            this.members.addAll(member);
            Log.d("Debug", "setting member");
        }
        Log.d("MEMBERS", String.valueOf(members));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout groupMemberView;
        Log.d("DEBUG", "in adapter getView");

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
        ImageView check = groupMemberView.findViewById(R.id.ResponseCheckIcon);
        ImageView cross = groupMemberView.findViewById(R.id.ResponseCrossIcon);
        ImageView noResponse = groupMemberView.findViewById(R.id.ResponseNone);

        //Map<String, String> user = (Map<String, String>) getItem(position);

        NewAccount.User user = getItem(position);
        if (user != null) {
            String name = user.getName();
            String email = user.getEmail();
            memberName.setText(name);
            memberEmail.setText(email);
            if (email != null) {
                myRef.child("users").child(email).child("profile_image").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        path = dataSnapshot.getValue(String.class);
                        Log.d("retrieved data snapshot", String.valueOf(dataSnapshot));
                        FoodItem item = new FoodItem("", "");
                        Log.d("DEBUG", "before set path");
                        item.setImage(path, memberImage);
                        Log.d("DEBUG10", "AFTER set path");
                        Log.d("DEBUG", "PATH: " + path);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("error - data snapshot", String.valueOf(databaseError));

                    }
                });
            }
        }
        
        Context context = getContext();
        memberImage.setImageDrawable(context.getDrawable(R.drawable.baseline_account_circle_24));

        //long response = (long) Integer.parseInt(user.get("response")); //im getting a really weird error here

        int response = user.getResponse();

        if (response == 0) {
            noResponse.setVisibility(View.VISIBLE);
            check.setVisibility(View.INVISIBLE);
            cross.setVisibility(View.INVISIBLE);
        }
        if (response == 1) {
            noResponse.setVisibility(View.INVISIBLE);
            check.setVisibility(View.VISIBLE);
            cross.setVisibility(View.INVISIBLE);
        }
        if (response == 2) {
            noResponse.setVisibility(View.INVISIBLE);
            check.setVisibility(View.INVISIBLE);
            cross.setVisibility(View.VISIBLE);
        }
        return groupMemberView;
    }

}
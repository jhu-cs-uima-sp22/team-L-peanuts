package com.example.peanuts;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/*public class ItemAdapter extends ArrayAdapter<Item> {
    int resource;
    String user;

    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://peanuts-e9a7c-default-rtdb.firebaseio.com/");
    private DatabaseReference myRef = database.getReference("users");
    private ArrayList<String> checkedItem;


    public ItemAdapter(Context ctx, int res, List<Item> items, String user)
    {
        super(ctx, res, items);
        resource = res;
        this.user = user;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("debug", "in item getView");
        LinearLayout itemView;
        Item it = getItem(position);

        if (convertView == null) {
            itemView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(inflater);
            vi.inflate(resource, itemView, true);
        } else {
            itemView = (LinearLayout) convertView;
        }

        ImageView imageView = (ImageView) itemView.findViewById(R.id.restriction_icon);
        TextView textView = (TextView) itemView.findViewById(R.id.restriction_name);
        CheckBox checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);

        myRef.child(user).child("restrictions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("debug", "in data snap");

                if (dataSnapshot.getValue() != null) {
                    Log.d("retrieve_success", dataSnapshot.toString());
                    checkedItem = (ArrayList<String>) dataSnapshot.getValue();
                    Log.d("debug", "got db value");

                    if (checkedItem != null && checkedItem.contains(it.getItem())) {
                        checkBox.setChecked(true);
                    }
                } else {
                    checkedItem = new ArrayList<>();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("retrieve_fail", databaseError.toString());
                checkedItem = new ArrayList<>();
            }
        });

        imageView.setImageDrawable(it.getIcon());
        textView.setText(it.getItem());

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.d("ITEM", "added");
                if (b && !checkedItem.contains(it.getItem())) {
                    checkedItem.add(it.getItem());
                    Log.d("ItemList", String.valueOf(checkedItem));
                } else if (!b && checkedItem.contains(it.getItem())) {
                    checkedItem.remove(it.getItem());
                }
                myRef.child(user).child("restrictions").setValue(checkedItem);
            }
        });

        return itemView;
    }
}

 */
package com.example.peanuts.ui.add;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.peanuts.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.peanuts.Item;

import java.util.List;

public class FoodPostAdapter extends ArrayAdapter<Item> {
    int resource;
    private ArrayList<Item> checkedItem;

    public FoodPostAdapter(Context ctx, int res, List<Item> items, ArrayList<Item> checkedItem) {
        super(ctx, res, items);
        resource = res;
        this.checkedItem = checkedItem;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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

        TextView textView = (TextView) itemView.findViewById(R.id.restriction_name);
        CheckBox checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);


        textView.setText(it.getItem());
        Boolean checked = it.isChecked();
        checkBox.setChecked(checked);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.d("ITEM", "added");
                it.changeChecked(b);
            }
        });

        return itemView;
    }

}

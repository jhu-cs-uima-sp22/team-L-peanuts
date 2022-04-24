package com.example.peanuts;

import android.content.Context;
import android.content.Intent;
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

import androidx.cardview.widget.CardView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class GroupAddFoodAdapter extends ArrayAdapter<FoodItem> {
    int resource;
    protected FirebaseDatabase database;
    protected DatabaseReference myRef;
    private ArrayList<FoodItem> addedItems;

    public GroupAddFoodAdapter(Context ctx, int res, List<FoodItem> items, ArrayList<FoodItem> added) {
        super(ctx, res, items);
        resource = res;
        addedItems = added;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("debug", "in getView");
        LinearLayout itemView;
        FoodItem it = getItem(position);

        if (convertView == null) {
            itemView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(inflater);
            vi.inflate(resource, itemView, true);
        } else {
            itemView = (LinearLayout) convertView;
        }

        TextView textView = (TextView) itemView.findViewById(R.id.restriction_name);
        ImageView image = (ImageView) itemView.findViewById(R.id.restriction_icon);
        CheckBox checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
        //TextView allergenText = (TextView) itemView.findViewById(R.id.allergens);
        addedItems = new ArrayList<>();
        //Title
        String name = it.getName();
        if (name != null) {
            textView.setText(name);
            Log.d("debug", "set name");
        } else {
            name = "Untitled";
            textView.setText(name);
        }

        String path = it.getImageUri();
        Log.d("debug", "got path");
        Log.d("debug", path);

        it.setImage(path, image);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.d("ITEM", "added");
                it.changeChecked(b);
                if (b) {
                    addedItems.add(it);
                } else {
                    addedItems.remove(it);
                }
            }
        });


        //Log.d("debug", "didn't have image");
        return itemView;
    }
}

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

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupAddFoodAdapter extends ArrayAdapter<FoodItem> {
    int resource;
    protected FirebaseDatabase database = FirebaseDatabase.getInstance("https://peanuts-e9a7c-default-rtdb.firebaseio.com/");
    protected DatabaseReference myRef = database.getReference();
    private ArrayList<FoodItem> addedItems;
    private String groupID;
    ArrayList<FoodItem> checkedItem;
    HashMap<String, FoodItem> ID;

    public GroupAddFoodAdapter(Context ctx, int res, List<FoodItem> items, ArrayList<FoodItem> added, String id) {
        super(ctx, res, items);
        resource = res;
        addedItems = added;
        groupID = id;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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
        checkedItem = new ArrayList<>();
        ID = new HashMap<>();

        //checkBox.setChecked(false);

        //Foods already in database

        myRef.child("groups").child(groupID).child("foods").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ids : dataSnapshot.getChildren()) {
                    String name = (String)ids.child("name").getValue();
                    String path = (String)ids.child("imageUri").getValue();
                    ArrayList<String> allergens = (ArrayList<String>) ids.child("allergens").getValue();
                    FoodItem item = new FoodItem(name, path, allergens);
                    ID.put(ids.getKey(), item);
                }

                Log.d("DEBUG1", "Items in DB map: " + ID);

                checkBox.setChecked(ID.containsValue(it));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*if (checkedItem != null && checkedItem.contains(it)) {
            checkBox.setChecked(true);
        }*/

        //Title
        String name = it.getName();
        if (name != null) {
            textView.setText(name);
        } else {
            name = "Untitled";
            textView.setText(name);
        }

        String path = it.getImageUri();

        it.setImage(path, image);

        //notifyDataSetChanged();
        Log.d("DEBUG1", "Items in DB map: " + ID);
        //checkBox.setChecked(ID.containsValue(it));

        //for(FoodItem item : checkedItem) {
        /*if (checkedItem != null) {
            if (checkedItem.contains(it)) {
                checkBox.setChecked(true);
            }
        }*/

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                it.changeChecked(b);

                if (b) {
                    Log.d("DEBUG1", "in check");
                    addedItems.add(it);
                } else {
                    Log.d("DEBUG1", "in remove");
                    checkBox.setChecked(false);
                    addedItems.remove(it);

                    if (ID.containsValue(it)) {
                        Log.d("DEBUG1", "in contains");
                        String foodID = "";
                        for (Map.Entry<String, FoodItem> entry : ID.entrySet()) {
                            if (entry.getValue().equals(it)) {
                                foodID = entry.getKey();
                                Log.d("DEBUG1", "key found");
                                Log.d("DEBUG1", "key: " + foodID);
                                break;
                            }
                        }
                        if (!foodID.equals("")) {
                            Log.d("DEBUG1", "before remove");
                            myRef.child("groups").child(groupID).child("foods").child(foodID).getRef().removeValue();
                            //notifyDataSetChanged();
                            Log.d("DEBUG1", "after remove");
                            Log.d("DEBUG1", "Map: " + ID);
                        }
                        //notifyDataSetChanged();
                    }
                }
                Log.d("DEBUG1", "Added items: " + addedItems);
                //Log.d("DEBUG1", "Items in DB map: " + ID);
            }
        });


        //Log.d("debug", "didn't have image");
        return itemView;
    }

    public ArrayList<FoodItem> getAddedItems () {
        return addedItems;
    }
}

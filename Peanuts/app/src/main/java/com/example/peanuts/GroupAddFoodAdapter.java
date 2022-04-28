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

        //Foods already in database

        myRef.child("groups").child(groupID).child("foods").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //if (dataSnapshot.getChildren() != null) {
                    //for (DataSnapshot foods : foodIDs.getChildren()) {
                for (DataSnapshot ids : dataSnapshot.getChildren()) {
                    String name = (String)ids.child("name").getValue();
                    Log.d("DEBUG", "NAME IS: " + name);
                    String path = (String)ids.child("imageUri").getValue();
                    Log.d("DEBUG", "PATH IS: " + path);
                    ArrayList<String> allergens = (ArrayList<String>) ids.child("allergens").getValue();
                    Log.d("DEBUG", "ALLERGENS ARE: " + allergens);
                    FoodItem item = new FoodItem(name, path, allergens);
                    ID.put(ids.getKey(), item);
                    //ids.getValue(FoodItem.class)
                    Log.d("DEBUG", "IDS ARE: " + ids);
                    //Log.d("DEBUG", "FOODS ARE: " + ids.);
                }

                Log.d("DEBUG", "THE MAP IS: " + ID);

                Log.d("DEBUG", "IT VALUE IS: " + it);

                //ID = (HashMap<String, FoodItem>)dataSnapshot.getChildren();
                if (ID.containsValue(it)) {
                    Log.d("DEBUG", "IN IF STATEMENT");
                    checkBox.setChecked(true);
                }

                Log.d("DEBUG", "ID AFTER FILLED: " + ID);
                /*for (Map.Entry<String, FoodItem> entry : ID.entrySet()) {
                    //checkedItem.add(entry.getValue());
                }*/
                //if (addedItems != null && )
                //addedItems = (ArrayList<FoodItem>) dataSnapshot.getValue();

                Log.d("retrieve_success GROUPS", dataSnapshot.toString());
                //checkedItem = (ArrayList<FoodItem>) dataSnapshot.getValue();
                /*if (checkedItem != null && checkedItem.contains(it)) {
                    checkBox.setChecked(true);
                }*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*if (checkedItem != null && checkedItem.contains(it)) {
            checkBox.setChecked(true);
        }*/

        Log.d("DEBUG", "Checked foods: " + ID);

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

        //for(FoodItem item : checkedItem) {
        /*if (checkedItem != null) {
            if (checkedItem.contains(it)) {
                checkBox.setChecked(true);
            }
        }*/

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.d("ITEM", "added");
                it.changeChecked(b);
                if (b) {
                    addedItems.add(it);
                    Log.d("DEBUG", "In checked item");
                    Log.d("DEBUG", "Item list in add item: " + addedItems);

                } else {
                    addedItems.remove(it);
                    if (ID.containsValue(it)) {
                        String foodID = "";
                        Log.d("DEBUG", "In contains");
                        for (Map.Entry<String, FoodItem> entry : ID.entrySet()) {
                            if (entry.getValue().equals(it)) {
                                foodID = entry.getKey();
                                Log.d("DEBUG", "Found key");
                                break;
                            }
                        }
                        if (!foodID.equals("")) {
                            myRef.child("groups").child("foods").child(foodID).getRef().removeValue();
                        }
                    }
                    Log.d("DEBUG", "In remove item");
                    Log.d("DEBUG", "Item list in remove item: " + addedItems);
                }
            }
        });


        //Log.d("debug", "didn't have image");
        return itemView;
    }

    public ArrayList<FoodItem> getAddedItems () {
        return addedItems;
    }
}

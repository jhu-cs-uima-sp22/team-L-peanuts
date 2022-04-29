package com.example.peanuts;

import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.example.peanuts.ui.notifications.NotificationItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GroupAddFoodAdapter extends ArrayAdapter<FoodItem> {
    int resource;
    protected FirebaseDatabase database = FirebaseDatabase.getInstance("https://peanuts-e9a7c-default-rtdb.firebaseio.com/");
    protected DatabaseReference myRef = database.getReference();
    //private ArrayList<FoodItem> addedItems;
    private String groupID;
    private ArrayList<FoodItem> checkedItem;
    HashMap<String, FoodItem> ID;
    protected CheckBox checkBox;
    protected FoodItem it;

    public GroupAddFoodAdapter(Context ctx, int res, List<FoodItem> items, ArrayList<FoodItem> added, String id) {
        super(ctx, res, items);
        resource = res;
        checkedItem = added;
        groupID = id;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout itemView;
        FoodItem it = getItem(position);
        //l = new Listener();

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
       // addedItems = new ArrayList<>();
        checkedItem = new ArrayList<>();


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

        myRef.child("groups").child(groupID).child("foods").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //ID = new HashMap<>();
                checkedItem = new ArrayList<>();
                Log.d("DEBUG8", "in data change");

                for (DataSnapshot ids : dataSnapshot.getChildren()) {
                    String name = (String) ids.child("name").getValue();
                    String path = (String) ids.child("imageUri").getValue();
                    ArrayList<String> allergens = (ArrayList<String>) ids.child("allergens").getValue();
                    FoodItem item = new FoodItem(name, path, allergens);
                    checkedItem.add(item);
                    //ID.put(ids.getKey(), item);
                }
                //notifyDataSetChanged();

                Log.d("DEBUG6", "Items in DB map: " + ID);

                //checkBox.setChecked(ID.containsValue(it));
                checkBox.setChecked(checkedItem.contains(it));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.d("DEBUG8", "in on checked");
                it.changeChecked(b);
                Log.d("DEBUG7", "CHECK: " + b);
                Log.d("DEBUG7", "ADDEDITEMS: " + checkedItem);

                if (b && !checkedItem.contains(it)) {
                    Log.d("DEBUG8", "in true");
                    Log.d("DEBUG1", "in check");
                    //addedItems.add(it);
                    checkedItem.add(it);
                } else if (!b && checkedItem.contains(it)) {
                    Log.d("DEBUG8", "in false");
                    Log.d("DEBUG1", "in remove");
                    //checkBox.setChecked(false);
                    String index = checkedItem.indexOf(it) + "";
                    //addedItems.remove(it);
                    checkedItem.remove(it);
                    //myRef.child("groups").child(groupID).child("foods").child(index).getRef().removeValue();

                }
            }
        });


        //Log.d("debug", "didn't have image");
        return itemView;
    }


    public ArrayList<FoodItem> getAddedItems () {
        return checkedItem;
    }
}

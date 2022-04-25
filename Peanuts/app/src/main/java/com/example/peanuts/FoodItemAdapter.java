package com.example.peanuts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FoodItemAdapter extends ArrayAdapter<FoodItem> {

    int resource;
    private String user;
    private List<FoodItem> items;
    private DatabaseReference myRefForFoods;
    private FirebaseDatabase databaseForFoods;
    private String imageUri;
    private String foodName;

    public FoodItemAdapter(Context ctx, int res, List<FoodItem> items, String user)
    {
        super(ctx, res, items);
        resource = res;
        this.items = items;
        this.user = user;
        databaseForFoods = FirebaseDatabase.getInstance("https://peanuts-e397e-default-rtdb.firebaseio.com/");
        myRefForFoods = databaseForFoods.getReference("Users");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout foodItemView;

        if (convertView == null) {
            foodItemView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(inflater);
            vi.inflate(resource, foodItemView, true);
        } else {
            foodItemView = (LinearLayout) convertView;
        }

        TextView name = (TextView) foodItemView.findViewById(R.id.food_item_name);
        ImageView image = (ImageView) foodItemView.findViewById(R.id.food_item_image);
        ImageButton close = (ImageButton) foodItemView.findViewById(R.id.close);
        Drawable closeImage = getContext().getDrawable(R.drawable.outline_close_24);
        close.setImageDrawable(closeImage);
        close.setTag(position);

        myRefForFoods.child(user).child("" + position).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                foodName = (String) dataSnapshot.child("name").getValue();
                name.setText(foodName);
                imageUri = (String) dataSnapshot.child("imageUri").getValue();
                if (imageUri != null) {
                    image.setImageURI(Uri.parse(imageUri));
                }
                close.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        Integer index = (Integer) view.getTag();
                        items.remove(index.intValue());
                        notifyDataSetChanged();
                        myRefForFoods.child(user).setValue(items);
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("retrieve_fail", databaseError.toString());
            }
        });

        return foodItemView;
    }

}

package com.example.peanuts;

import android.content.Context;
import android.net.Uri;
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

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FoodItemAdapterProfile extends ArrayAdapter<FoodItem> {

    int resource;
    private String user;
    private DatabaseReference myRefForFoods;
    private FirebaseDatabase databaseForFoods;
    private String imageUri;


    public FoodItemAdapterProfile(Context ctx, int res, List<FoodItem> items, String user)
    {
        super(ctx, res, items);
        resource = res;
        this.user = user;
        databaseForFoods = FirebaseDatabase.getInstance("https://peanuts-e397e-default-rtdb.firebaseio.com/");
        myRefForFoods = databaseForFoods.getReference("Users");
        //imageUri = "content://com.android.providers.media.documents/document/image%3A35";
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout foodItemView;
        FoodItem it = getItem(position);

        if (convertView == null) {
            foodItemView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(inflater);
            vi.inflate(resource, foodItemView, true);
        } else {
            foodItemView = (LinearLayout) convertView;
        }

        myRefForFoods.child(user).child("" + position).child("imageUri").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("debug", "Position: " + position );
                Log.d("debug", "Data: " + dataSnapshot);

                imageUri = (String) dataSnapshot.getValue();
                ImageView foodButton = (ImageView) foodItemView.findViewById(R.id.food_item_image_profile);
//                foodButton.setImageURI(Uri.parse(imageUri));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("retrieve_fail", databaseError.toString());
            }
        });


        return foodItemView;
    }

}

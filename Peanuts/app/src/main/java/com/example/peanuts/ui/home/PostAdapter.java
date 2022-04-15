package com.example.peanuts.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
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

import com.example.peanuts.FoodItem;
import com.example.peanuts.R;
import com.example.peanuts.ui.add.FoodPost;
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

public class PostAdapter extends ArrayAdapter<FoodItem> {
    int resource;
    protected FirebaseDatabase database;
    protected DatabaseReference myRef;
    private SharedPreferences sp;
    private ArrayList<FoodItem> usersPost;

    public PostAdapter(Context ctx, int res, List<FoodItem> items) {
        super(ctx, res, items);
        Log.d("debug", "in post constructor");
        resource = res;
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

        TextView textView = (TextView) itemView.findViewById(R.id.name_text);
        ImageView image = (ImageView) itemView.findViewById(R.id.image);
//Fill arraylist
        //database = FirebaseDatabase.getInstance("https://peanuts-e397e-default-rtdb.firebaseio.com/");
        //myRef = database.getReference("Users");
        //usersPost = new ArrayList<>();
        //Context context = getContext();
        //sp = PreferenceManager.getDefaultSharedPreferences(context);
        //String email = sp.getString("user_email", "email");

        /*myRef.child(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("debug", "in onDataChange");

                if (dataSnapshot.getValue() != null) {
                    Log.d("retrieve_success", dataSnapshot.toString());
                    for (DataSnapshot posts: dataSnapshot.getChildren()) {
                        usersPost.add(posts.getValue(FoodItem.class));
                        Log.d("debug", "added child in adapt");
                    }

                    //usersPost.remove(usersPost.size()-1);

                } else {
                    //usersPost = new ArrayList<>();
                    Log.d("debug", "in empty");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("retrieve_fail", databaseError.toString());
            }
        });*/

        String name = it.getName();
        if (name != null) {
            textView.setText(name);
            Log.d("debug", "set name");
        } else {
            name = "Untitled";
            textView.setText(name);
        }

        if (it.getImageUri() != null ) {
            Uri foodImage = Uri.parse(it.getImageUri());
            image.setImageURI(foodImage);
        }
        Log.d("debug", "set image");
        return itemView;
    }

}
package com.example.peanuts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
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
    private StorageReference storageReference;


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
        //ArrayList<Integer> foods =
        FoodItem it = getItem(position);

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

        /*foodName = it.getName();
        name.setText(foodName);
        it.setImage(it.getImageUri(), image);

        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Integer index = (Integer) view.getTag();
                items.remove(index.intValue());
                notifyDataSetChanged();
                myRefForFoods.child(user).setValue(items);
            }
        });*/

        myRefForFoods.child(user).child("" + position).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                foodName = (String) dataSnapshot.child("name").getValue();
                name.setText(foodName);
                imageUri = (String) dataSnapshot.child("imageUri").getValue();
                setImage(imageUri, image);
                /*if (imageUri != null) {
                    image.setImageURI(Uri.parse(imageUri));
                }*/
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

    public void setImage (String path, ImageView image) {
        if (path != null && !path.equals("")) {
            //get the storage reference
            storageReference = FirebaseStorage.getInstance("gs://peanuts-e9a7c.appspot.com").getReference().child(path);
            Log.d("debug", "got storage ref");

            //create temp file for image
            File file = null;
            try {
                file = File.createTempFile("images", "jpg");
            } catch (IOException e) {
                e.printStackTrace();
            }

            File finalLocalFile = file;
            Log.d("debug", "got file");

            //store to storage
            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Log.d("debug", "in on success for retrieving image");
                    Bitmap bitmap = BitmapFactory.decodeFile(finalLocalFile.getAbsolutePath());
                    image.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("debug", "in on failure for retrieving image");

                }
            });
        }
    }

}

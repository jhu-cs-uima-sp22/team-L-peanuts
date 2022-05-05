package com.example.peanuts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

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

public class FoodItemAdapterProfile extends ArrayAdapter<FoodItem> {

    int resource;
    private String user;
    private DatabaseReference myRefForFoods;
    private FirebaseDatabase databaseForFoods;
    private String imageUri;
    private StorageReference storageReference;
    private FirebaseDatabase databaseUsers;


    public FoodItemAdapterProfile(Context ctx, int res, List<FoodItem> items, String user)
    {
        super(ctx, res, items);
        resource = res;
        this.user = user;
        databaseForFoods = FirebaseDatabase.getInstance("https://peanuts-e397e-default-rtdb.firebaseio.com/");
        myRefForFoods = databaseForFoods.getReference("Users");

        databaseUsers = FirebaseDatabase.getInstance("https://peanuts-e9a7c-default-rtdb.firebaseio.com/");
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

       myRefForFoods.child(user).child("" + position).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //image uri
                imageUri = (String) dataSnapshot.child("imageUri").getValue();
                ImageView foodButton = (ImageView) foodItemView.findViewById(R.id.food_item_image_profile);
                setImage(imageUri, foodButton);

                foodButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Context context = getContext();
                        Intent intent = new Intent(context, FoodDetail.class);
                        String name = (String) dataSnapshot.child("name").getValue();
                        ArrayList<String> list = (ArrayList<String>) dataSnapshot.child("allergens").getValue();
                        boolean[] restrictions = new boolean[12];
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                        for (int i = 0; i < 12; i++) {
                            if (list != null && list.contains(preferences.getString("" + i, ""))) {
                                restrictions[i] = true;
                            }
                        }
                        intent.putExtra("position", position);
                        intent.putExtra("user", user);
                        intent.putExtra("name", name);
                        intent.putExtra("restrictions", restrictions);
                        intent.putExtra("image", imageUri);
                        context.startActivity(intent);
                        notifyDataSetChanged();
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return foodItemView;
    }

    public void setImage (String path, ImageView image) {
        if (path != null && !path.equals("")) {
            //get the storage reference
            storageReference = FirebaseStorage.getInstance("gs://peanuts-e9a7c.appspot.com").getReference().child(path);

            //create temp file for image
            File file = null;
            try {
                file = File.createTempFile("images", "jpg");
            } catch (IOException e) {
                e.printStackTrace();
            }

            File finalLocalFile = file;

            //store to storage
            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(finalLocalFile.getAbsolutePath());
                    image.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
    }

}

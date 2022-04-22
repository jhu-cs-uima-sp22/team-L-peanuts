package com.example.peanuts.ui.home;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.widget.ArrayAdapter;


import com.example.peanuts.RestrictionItem;

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
        TextView allergenText = (TextView) itemView.findViewById(R.id.allergens);
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
s                    Log.d("retrieve_success", dataSnapshot.toString());
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
        ArrayList<String> foods = it.getAllergens();
        String str = "Allergens: ";
        if (foods != null) {
            for (int i = 0; i < foods.size(); i++) {
                if (i < foods.size() - 1) {
                    str = str.concat(foods.get(i) + ", \n");
                }
            }

            allergenText.setText(str);
        }

        /*if (it.getImageUri() != null ) {
            Log.d("debug", "in if set image");
            String str = it.getImageUri();
            //Uri foodImage = Uri.parse(str);

            File file = it.getFile();
            if (file.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                image.setImageBitmap(myBitmap);
            }
            Log.d("debug", "set image, converted");

            //Bitmap bitmap = stringToBitmap(str);
            Log.d("debug", "set image, converted to bitmap");

            image.setVisibility(View.VISIBLE);
            //image.setImageURI(foodImage);
            //image.setImageURI(foodImage);
            //image.setImageDrawable(foodImage);
            Log.d("debug", "set image");
        }*/
        Log.d("debug", "set image");
        return itemView;
    }

    /*public Bitmap loadFromUri(Uri photoUri) {
        Log.d("debug", "in loadFromUri");
        Bitmap image = null;
        try {
            if(Build.VERSION.SDK_INT > 27){
                Log.d("debug", "in first if");
                // on newer versions of Android, use the new decodeBitmap method
                ImageDecoder.Source source = ImageDecoder.createSource(this.getContext().getContentResolver(), photoUri);
                Log.d("debug", "set source");
                image = ImageDecoder.decodeBitmap(source);
                Log.d("debug", "set image");
            } else {
                Log.d("debug", "in else");
                // support older versions of Android by using getBitmap
                image = MediaStore.Images.Media.getBitmap(this.getContext().getContentResolver(), photoUri);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("debug", "returned");
        return image;
    }*/

    public static Bitmap stringToBitmap(String encodedString){
        try{
            //String base64Image = encodedString.split(",")[1];
            String base64Image = encodedString.substring(8);
            byte [] encodeByte = Base64.decode(base64Image,Base64.URL_SAFE);

            Log.d("debug", "converted");
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        }
        catch(Exception e){
            Log.d("App", "Failed to decode image " + e.getMessage());
            return null;
        }
    }

}
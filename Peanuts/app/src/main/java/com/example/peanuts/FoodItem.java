package com.example.peanuts;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FoodItem {
    private String name;
    private boolean[] restrictions;
    private ArrayList<String> allergens;
    private String imageUri;
    private Drawable picture;
    private File file;
    int index;
    private byte[] data;
    private String randomID;
    private StorageReference storageReference;
    private boolean isChecked;


    FoodItem() {}

    FoodItem(String name, boolean[] restrictions) {
        this.name = name;
        this.restrictions = restrictions;
    }

    public FoodItem(String name, String imageUri) {
        this.name = name;
        Log.d("debug", "name is set");
        this.imageUri = imageUri;
        allergens = new ArrayList<>();
        index = 0;
    }

    public FoodItem(String name, String imageUri, ArrayList<String> allergens) {
        this.name = name;
        Log.d("debug", "name is set");
        this.imageUri = imageUri;
        //allergens = new ArrayList<>();
        this.allergens = allergens;
        index = 0;
    }

    public FoodItem(String name, byte[] b) {
        this.name = name;
        this.data = b;
        Log.d("debug", "name is set");
        allergens = new ArrayList<>();
        index = 0;
    }

    public FoodItem(String name, File file) {
        this.name = name;
        Log.d("debug", "name is set");
        this.file = file;
        allergens = new ArrayList<>();
        index = 0;
    }

    FoodItem(String name, boolean[] restrictions, Drawable picture) {
        this.name = name;
        this.restrictions = restrictions;
        this.picture = picture;
    }

    public byte[] getData() {
        return data;
    }

    public String getName() {
        return name;
    }

    public boolean[] getRestrictions() {
        return restrictions;
    }

    public Drawable getImage() {
        return picture;
    }

    public String getImageUri () {
        return imageUri;
    }

    public void addAllergens (String al) {
        allergens.add(index, al);
        index++;
    }

    /*public void setRandomID (String str) {
        randomID = str;
        hasImage = true;
    }*/

    /*public String getRandomID () {
        return randomID;
    }*/

    public File getFile () {
        return file;
    }

    public ArrayList<String> getAllergens () {
        if (allergens == null) {
            return null;
        }
        return allergens;
    }

    public boolean changeChecked(boolean b) {
        isChecked = b;
        return isChecked;
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

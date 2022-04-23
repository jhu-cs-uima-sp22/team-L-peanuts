package com.example.peanuts;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
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
    private boolean hasImage;

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

    public boolean getHasImage () {
        return hasImage;
    }
}

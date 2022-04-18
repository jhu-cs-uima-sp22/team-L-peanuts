package com.example.peanuts;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;

public class FoodItem {
    private String name;
    private boolean[] restrictions;
    private ArrayList<String> allergens;
    private String imageUri;
    private Drawable picture;
    int index;

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

    FoodItem(String name, boolean[] restrictions, Drawable picture) {
        this.name = name;
        this.restrictions = restrictions;
        this.picture = picture;
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

    public ArrayList<String> getAllergens () {
        return allergens;
    }
}

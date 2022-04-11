package com.example.peanuts.ui.add;

import android.net.Uri;

import java.util.ArrayList;

public class FoodPost {
    private String title;
    private ArrayList<String> allergens;
    private Uri imageUri;
    private int index;

    FoodPost (String title, Uri imageUri) {
        this.title = title;
        this.imageUri = imageUri;
        allergens = new ArrayList<>();
    }

    FoodPost (String title) {
        this.title = title;
        allergens = new ArrayList<>();
        index = 0;
    }

    public String getTitle () {
        return title;
    }

    public ArrayList<String> getAllergens () {
        return allergens;
    }

    public void addAllergens (String al) {
        allergens.add(index, al);
        index++;
    }

    public Uri getImageUri () {
        return imageUri;
    }
}

package com.example.peanuts;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.widget.ImageView;

import java.util.ArrayList;

public class FoodItem {
    private String name;
    private boolean[] restrictions;
    private Drawable picture;

    FoodItem(String name, boolean[] restrictions) {
        this.name = name;
        this.restrictions = restrictions;
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
}

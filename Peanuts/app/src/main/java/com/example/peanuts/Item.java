package com.example.peanuts;

import android.graphics.drawable.Drawable;

public class Item {
    private String item;
    private Boolean isChecked;
    private Drawable icon;

    public Item(String item, Boolean isChecked, Drawable icon) {
        this.item = item;
        this.isChecked = isChecked;
        this.icon = icon;
    }

    public String getItem() {
        return item;
    }

    public Boolean isChecked() {
        return isChecked;
    }

    public void changeChecked(Boolean checked) {
        isChecked = checked;
    }

    public Drawable getIcon() {
        return icon;
    }

}

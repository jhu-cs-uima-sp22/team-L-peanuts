package com.example.peanuts;

public class Item {
    private String item;
    private Boolean isChecked;

    public Item(String item, Boolean isChecked) {
        this.item = item;
        this.isChecked = isChecked;
    }

    public String getItem() {
        return item;
    }

    public Boolean isChecked() { return isChecked; }

    public void changeChecked(Boolean checked) {
        isChecked = checked;
    }
}

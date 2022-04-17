package com.example.peanuts;

import java.util.ArrayList;

public class GroupItem {

    private String name;
    private String hostEmail;
    private ArrayList<String> memberEmails;
    private ArrayList<FoodItem> foods;
    private boolean[] restrictions;

    public GroupItem(String name, String hostEmail, ArrayList<String> memberEmails) {
        this.name = name;
        this.hostEmail = hostEmail;
        this.memberEmails = memberEmails;
        restrictions = new boolean[12];
        for (String memberEmail : memberEmails) {
            //use database to populate restrictions
        }
    }

    public String getName() {
        return name;
    }

    public String getHost() {
        return hostEmail;
    }

    public ArrayList<String> getMembers() {
        return memberEmails;
    }

    public ArrayList<FoodItem> getFoods() {
        return foods;
    }

    public boolean[] getRestrictions() {
        return restrictions;
    }

    public void addFood(FoodItem food) {
        foods.add(food);
    }
}

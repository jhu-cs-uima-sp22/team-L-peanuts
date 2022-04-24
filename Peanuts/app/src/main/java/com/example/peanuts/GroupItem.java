package com.example.peanuts;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

public class GroupItem {
    private List<NewAccount.User> members;
    private List<String> restrictions;
    private List<FoodItem> foods;
    private String groupName;
    private String host;
    private String id;

    public GroupItem(String groupName, List<NewAccount.User> members, List<String> restrictions, String host, String id) {
        this.groupName = groupName;
        this.members = members;
        this.restrictions = restrictions;
        this.host = host;
        foods = new ArrayList<>();
        this.id = id;
    }

    public GroupItem(String groupName, List<NewAccount.User> members, List<String> restrictions, String host, List<FoodItem> foods) {
        this.groupName = groupName;
        this.members = members;
        this.restrictions = restrictions;
        this.host = host;
        this.foods = foods;
    }

    public String getGroupName() { return groupName;}

    public List<NewAccount.User> getMembers() { return members; }

    public List<String> getRestrictions() { return  restrictions; }

    public String getHost() { return host; }

    public List<FoodItem> getFoods() { return foods; }

    public String getId () {
        return id;
    }

}

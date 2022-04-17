package com.example.peanuts;

import java.util.ArrayList;
import java.util.List;

import kotlin.collections.ArrayDeque;

public class GroupItem {
    private List<NewAccount.User> members;
    private List<String> restrictions;
    private List<FoodItem> foods;
    private String groupName;

    public GroupItem(String name, List<NewAccount.User> members) {
        this.groupName = name;
        this.members = members;
        restrictions = new ArrayList<>();
        for (NewAccount.User member : members) {
            //use database to populate restrictions
        }
        foods = new ArrayList<>();
    }

    public String getGroupName() { return groupName;}

    public List<NewAccount.User> getMembers() { return members; }

    public List<String> getRestrictions() { return  restrictions; }

    public List<FoodItem> getFoods() { return foods; }



}

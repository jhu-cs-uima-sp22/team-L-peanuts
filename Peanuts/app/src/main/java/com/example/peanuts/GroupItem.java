package com.example.peanuts;

import java.util.ArrayList;
import java.util.List;

public class GroupItem {
    private final List<NewAccount.User> members;
    private final List<String> restrictions;
    private final List<FoodItem> foods;
    private final String groupName;
    private final String host;

    public GroupItem(String name, List<NewAccount.User> members, List<String> restrictions, String user) {
        this.groupName = name;
        this.members = members;
        this.restrictions = restrictions;
        this.host = user;

        foods = new ArrayList<>();
    }

    public String getGroupName() { return groupName;}

    public List<NewAccount.User> getMembers() { return members; }

    public List<String> getRestrictions() { return  restrictions; }

    public String getHost() { return host; }

    public List<FoodItem> getFoods() { return foods; }



}

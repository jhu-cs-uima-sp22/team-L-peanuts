package com.example.peanuts;

import java.util.ArrayList;
import java.util.List;

public class GroupItem {
    private List<NewAccount.User> members;
    private List<String> restrictions;
    private List<FoodItem> foods;
    private String groupName;
    private boolean isHost;
    private String host;

    public GroupItem(String name, List<NewAccount.User> members, List<String> restrictions, boolean isHost, String user) {
        this.groupName = name;
        this.members = members;
        this.restrictions = restrictions;
        this.host = user;
        foods = new ArrayList<>();
        this.isHost = isHost;
    }

    public String getGroupName() { return groupName;}

    public List<NewAccount.User> getMembers() { return members; }

    public List<String> getRestrictions() { return  restrictions; }

    public String getHost() { return host; }

    public List<FoodItem> getFoods() { return foods; }

    public boolean isHost() {
        return isHost;
    }



}

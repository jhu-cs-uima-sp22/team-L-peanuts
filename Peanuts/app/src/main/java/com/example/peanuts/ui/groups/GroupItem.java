package com.example.peanuts.ui.groups;

import com.example.peanuts.FoodItem;
import com.example.peanuts.NewAccount;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GroupItem {
    private List<NewAccount.User> members;
    private Map<String, List<String>> restrictions;
    private List<FoodItem> foods;
    private String groupName;
    private String host;
    private String id;

    public GroupItem(String groupName, List<NewAccount.User> members, Map<String, List<String>> restrictions, String host, String id) {
        this.groupName = groupName;
        this.members = members;
        this.restrictions = restrictions;
        this.host = host;
        foods = new ArrayList<>();
        this.id = id;
    }

    public GroupItem(String groupName, List<NewAccount.User> members, Map<String, List<String>> restrictions, String host, List<FoodItem> foods) {
        this.groupName = groupName;
        this.members = members;
        this.restrictions = restrictions;
        this.host = host;
        this.foods = foods;
    }

    public String getGroupName() { return groupName;}

    public List<NewAccount.User> getMembers() { return members; }

    public Map<String, List<String>> getRestrictions() { return restrictions; }

    public String getHost() { return host; }

    public List<FoodItem> getFoods() { return foods; }

    public String getId () {
        return id;
    }

}

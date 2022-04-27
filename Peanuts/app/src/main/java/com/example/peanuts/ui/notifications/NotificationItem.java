package com.example.peanuts.ui.notifications;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NotificationItem {

    String groupName;
    boolean isGroupInvite; //true for new group, false for menu change
    String owner;
    String group;
    String date;

    public NotificationItem(String groupName, String type, String group, String date) { //menu change
        this.groupName = groupName;
        this.isGroupInvite = type.equals("true");
        this.group = group;
        this.date = date;
    }

    NotificationItem (String groupName, boolean type, String owner) { //new group
        this.groupName = groupName;
        this.isGroupInvite = type;
        this.owner = owner;
    }

    public String getGroupName() {
        return groupName;
    }

    public boolean isGroupInvite() {
        return isGroupInvite;
    }

    public String getGroup() { return group; }

    public String getDate() { return date; }

    public String getOwner() {
        return owner;
    }
}

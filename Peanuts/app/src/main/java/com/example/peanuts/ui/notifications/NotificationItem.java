package com.example.peanuts.ui.notifications;

public class NotificationItem {

    String groupName;
    boolean type; //true for new group, false for menu change
    String owner;

    NotificationItem (String groupName, boolean type) { //menu change
        this.groupName = groupName;
        this.type = type;
    }

    NotificationItem (String groupName, boolean type, String owner) { //new group
        this.groupName = groupName;
        this.type = type;
        this.owner = owner;
    }

    public String getGroupName() {
        return groupName;
    }

    public boolean getType() {
        return type;
    }

    public String getOwner() {
        return owner;
    }
}

package com.example.peanuts.ui.notifications;

public class NotificationItem {

    String groupName;
    boolean isGroupInvite; //true for new group, false for menu change
    String owner;
    String group;

    public NotificationItem(String groupName, String type, String group) { //menu change
        this.groupName = groupName;
        this.isGroupInvite = type.equals("true");
        this.group = group;
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

    public String getOwner() {
        return owner;
    }
}

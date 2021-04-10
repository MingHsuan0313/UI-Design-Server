package com.selab.uidesignserver.entity.uiComposition;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Groups")
public class GroupsTable {

    @Id
    @Column(name="groupID", nullable = false)
    private String groupID;

    @Column(name="groupName", nullable = false)
    private String groupName;

    public GroupsTable(String groupID, String groupName){
        this.groupID = groupID;
        this.groupName = groupName;
    }

    public GroupsTable(){}

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupID() {
        return groupID;
    }

    public String getGroupName() {
        return groupName;
    }
}

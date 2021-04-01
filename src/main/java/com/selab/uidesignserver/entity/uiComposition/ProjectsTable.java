package com.selab.uidesignserver.entity.uiComposition;

import com.selab.uidesignserver.entity.authentication.GroupsTable;

import javax.persistence.*;

@Entity
@Table
public class ProjectsTable {
    @Id
    @Column(name="projectID", nullable = false)
    private String projectID;

    @Id
    @Column(name="projectName", nullable = false)
    private String projectName;

    @ManyToOne
    @JoinColumn(name = "groupID", referencedColumnName = "groupID")
    private GroupsTable groupsTable;




    public ProjectsTable(){}

    public ProjectsTable(String projectID, GroupsTable groupsTable, String projectName){
        this.projectID = projectID;
        this.groupsTable = groupsTable;
        this.projectName = projectName;
    }

    public GroupsTable getGroupsTable() {
        return groupsTable;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setGroupsTable(GroupsTable groupsTable) {
        this.groupsTable = groupsTable;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }
}

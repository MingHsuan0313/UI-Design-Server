package com.selab.uidesignserver.entity.uiComposition;

import javax.persistence.*;

@Entity
@Table(name = "Projects")
public class ProjectsTable {
    @Id
    @Column(name="projectID", nullable = false)
    private String projectID;
    
    @Column(name="projectName", nullable = false)
    private String projectName;

	@Column(name = "layout", nullable = true)
	private String layout;

    @OneToOne
    @JoinColumn(name = "groupID", referencedColumnName = "groupID", nullable=false)
    private GroupsTable groupsTable;

    public ProjectsTable(){}

    public ProjectsTable(String projectID, String projectName, GroupsTable groupsTable, String layout){
        this.projectID = projectID;
        this.groupsTable = groupsTable;
        this.projectName = projectName;
        this.layout = layout;
    }

    public String getLayout() {
        return this.layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
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

    public String getProjectName() {
        return projectName;
    }
}

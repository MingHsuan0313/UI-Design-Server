package com.selab.uidesignserver.entity.uiComposition;

import javax.persistence.*;

@Entity
@Table(name = "Themes")
public class ThemesTable {
    @Id
    @Column(name = "themeID", nullable = false)
    private String id;

    @ManyToOne
    @JoinColumn(name = "projectID", referencedColumnName = "projectID")
    private ProjectsTable projectsTable;

    @ManyToOne
    @JoinColumn(name = "ownerID", referencedColumnName = "userID")
    private UsersTable usersTable;

    @Column(name = "themeName", nullable = false)
    private String themeName;

    @Column(name = "used", nullable = false)
    private Boolean used;

    public ThemesTable() {

    }

    public ThemesTable(String id, String themeName, ProjectsTable projectsTable, UsersTable usersTable, Boolean used) {
        this.id = id;
        this.projectsTable = projectsTable;
        this.themeName = themeName;
        this.usersTable = usersTable;
        this.used = used;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsersTable(UsersTable usersTable) {
        this.usersTable = usersTable;
    }

    public void setProjectsTable(ProjectsTable projectsTable) {
        this.projectsTable = projectsTable;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public UsersTable getUsersTable() {
        return usersTable;
    }

    public Boolean getUsed() {
        return used;
    }

    public ProjectsTable getProjectsTable() {
        return projectsTable;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }
}
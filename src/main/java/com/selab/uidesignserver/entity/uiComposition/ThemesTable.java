package com.selab.uidesignserver.entity.uiComposition;

import javax.persistence.*;

@Entity
@Table(name = "Themes")
public class ThemesTable {
    @Id
    @Column(name = "themeID")
    private String id;

    @Column(name = "projectName")
    private String projectName;

    @Column(name = "themeName")
    private String themeName;

    public ThemesTable() {

    }

    public ThemesTable(String id, String projectName, String themeName) {
        this.id = id;
        this.projectName = projectName;
        this.themeName = themeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }
}
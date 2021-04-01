package com.selab.uidesignserver.entity.uiComposition;

import javax.persistence.*;

@Entity
@Table(name = "SUMDLs")
public class SumdlsTable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sumdlID")
    private int id;

    @Column(name = "sumdl", nullable = false)
    private String sumdl;

    @ManyToOne
    @JoinColumn(name = "projectID", referencedColumnName = "projectID")
    private ProjectsTable projectsTable;

    @ManyToOne
    @JoinColumn(name = "themeID", referencedColumnName = "themeID")
    private ThemesTable themesTable;

    @ManyToOne
    @JoinColumn(name = "pageID", referencedColumnName = "pageID")
    private PagesTable pagesTable;

    public SumdlsTable() {

    }

    public SumdlsTable(String sumdl, ProjectsTable projectsTable, ThemesTable themesTable, PagesTable pagesTable) {
        this.sumdl = sumdl;
        this.projectsTable = projectsTable;
        this.themesTable = themesTable;
        this.pagesTable = pagesTable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ThemesTable getThemesTable() {
        return themesTable;
    }

    public void setThemesTable(ThemesTable themesTable) {
        this.themesTable = themesTable;
    }

    public PagesTable getPagesTable() {
        return pagesTable;
    }

    public void setPagesTable(PagesTable pagesTable) {
        this.pagesTable = pagesTable;
    }

    public ProjectsTable getProjectsTable() {
        return projectsTable;
    }

    public void setProjectsTable(ProjectsTable projectsTable) {
        this.projectsTable = projectsTable;
    }

    public String getSumdl() {
        return sumdl;
    }

    public void setSumdl(String sumdl) {
        this.sumdl = sumdl;
    }
}
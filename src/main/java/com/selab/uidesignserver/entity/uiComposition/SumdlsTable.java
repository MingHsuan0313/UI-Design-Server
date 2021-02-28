package com.selab.uidesignserver.entity.uiComposition;

import javax.persistence.*;

@Entity
@Table(name = "SUMDLs")
public class SumdlsTable {

    @Id
    @Column(name = "sumdlID")
    private int id;

    @Column(name = "sumdl")
    private String sumdl;

    @Column(name = "projectName")
    private String projectName;

    public SumdlsTable() {

    }

    public SumdlsTable(int id, String sumdl, String projectName) {
        this.id = id;
        this.sumdl = sumdl;
        this.projectName = projectName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getSumdl() {
        return sumdl;
    }

    public void setSumdl(String sumdl) {
        this.sumdl = sumdl;
    }
}
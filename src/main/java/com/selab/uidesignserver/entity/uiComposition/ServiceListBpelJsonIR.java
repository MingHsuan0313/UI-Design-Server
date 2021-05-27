package com.selab.uidesignserver.entity.uiComposition;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ServiceListBpelJsonIRs")
public class ServiceListBpelJsonIR {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "selector")
    private String selector;

    @Column(name = "content", columnDefinition = "MEDIUMTEXT")
    private String content;

    @JsonProperty("created_at")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "projectID", referencedColumnName = "projectID")
    private ProjectsTable projectsTable;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "themeID", referencedColumnName = "themeID")
    private ThemesTable themesTable;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "pageID", referencedColumnName = "pageID")
    private PagesTable pagesTable;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ProjectsTable getProjectsTable() {
        return projectsTable;
    }

    public void setProjectsTable(ProjectsTable projectsTable) {
        this.projectsTable = projectsTable;
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
}

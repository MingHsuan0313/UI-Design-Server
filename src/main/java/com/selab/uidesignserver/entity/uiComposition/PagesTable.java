package com.selab.uidesignserver.entity.uiComposition;

import javax.persistence.*;

@Entity
@Table(name = "PageUICDLs")
public class PagesTable {

	@Id
    @Column(name = "pageID", nullable = false)
    private String id;
    
	@Column(name = "pageName", nullable = false)
	private String name;
    
    @Column(name = "pageUICDL", nullable = false)
    private String pdl;

	@ManyToOne
	@JoinColumn(name = "projectID", referencedColumnName = "projectID")
	private ProjectsTable projectsTable;

	@ManyToOne
	@JoinColumn(name = "themeID", referencedColumnName = "themeID")
	private ThemesTable themeTable;
	
	public PagesTable() {
		
	}

	public PagesTable(String id, String name, String pdl, ThemesTable themesTable, ProjectsTable projectsTable) {
		this.id = id;
		this.name = name;
		this.pdl = pdl;
		this.themeTable = themesTable;
		this.projectsTable = projectsTable;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPdl() {
		return pdl;
	}

	public void setPdl(String pdl) {
		this.pdl = pdl;
	}

	public ProjectsTable getProjectsTable() {
		return projectsTable;
	}

	public void setProjectsTable(ProjectsTable projectsTable) {
		this.projectsTable = projectsTable;
	}

	public ThemesTable getThemeTable() {
		return themeTable;
	}
	
	public void setThemeTable(ThemesTable themeTable) {
		this.themeTable = themeTable;
	}
}
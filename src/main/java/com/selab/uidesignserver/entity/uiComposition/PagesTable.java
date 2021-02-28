package com.selab.uidesignserver.entity.uiComposition;

import javax.persistence.*;

@Entity
@Table(name = "PageUICDLs")
public class PagesTable {

	@Id
    @Column(name = "pageID")
    private int id;
    
	@Column(name = "pageName")
	private String name;
    
    @Column(name = "pageUICDL")
    private String pdl;

	@Column(name = "projectName")
	private String projectName;

	@ManyToOne
	@JoinColumn(name = "themeID", referencedColumnName = "themeID")
	private ThemesTable themeTable;
	
	public PagesTable() {
		
	}

	public PagesTable(int id, String name, String pdl, String projectName, ThemesTable themesTable) {
		this.id = id;
		this.name = name;
		this.pdl = pdl;
		this.projectName = projectName;
		this.themeTable = themesTable;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public ThemesTable getThemeTable() {
		return themeTable;
	}
	
	public void setThemeTable(ThemesTable themeTable) {
		this.themeTable = themeTable;
	}
}